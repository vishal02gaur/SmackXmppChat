package vishal.chatdemo.managers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import vishal.chatdemo.BuildConfig;
import vishal.chatdemo.Constants;
import vishal.chatdemo.MessageEvent;
import vishal.chatdemo.State;
import vishal.chatdemo.messages.MessageObj;
import vishal.chatdemo.messages.TextMessage;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public class ChatMessageManager {
    private static String TAG = ChatMessageManager.class.getSimpleName();
    private BlockingQueue<MessageObj> mMessageObjQueue;
    private List<MessageObj> messageObjList;
    private Thread thread;
    private static ChatMessageManager messageManager;

    public static ChatMessageManager getMessageManager() {
        if (messageManager == null)
            messageManager = new ChatMessageManager();
        return messageManager;
    }

    private ChatMessageManager() {
        mMessageObjQueue = new LinkedBlockingQueue<MessageObj>();
        messageObjList = new ArrayList<>();
        thread = new Thread(queueController);
        thread.start();
        EventBus.getDefault().register(this);
    }

    public void addMessage(MessageObj messageObj) {
        messageObjList.add(messageObj);
        mMessageObjQueue.offer(messageObj);
    }

    public List<MessageObj> getMessageObjList() {
        return messageObjList;
    }

    Runnable queueController = new Runnable() {
        public void run() {
            MessageObj messageObjObj;
            while (true) {
                try {
                    messageObjObj = mMessageObjQueue.take();
                    if (null != messageObjObj) {
                        handleRequest(messageObjObj);
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Request Found.");
                        }
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Queue Interrupted", e);
                    }
                }
            }
        }
    };

    private void handleRequest(MessageObj messageObj) throws XmppStringprepException {
        Message message = new Message();

        EntityBareJid _tojid = JidCreate.entityBareFrom(messageObj.get_to());
        EntityBareJid _fromjid = JidCreate.entityBareFrom(messageObj.get_from());
        message.setStanzaId(messageObj.get_id());
        message.setTo(_tojid);
        message.setFrom(_fromjid);
        message.setType(Message.Type.chat);
        message.setBody(messageObj.getMsg());


        try {
            XMPPConnectionManager.getXmppConnectionManager().sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(Message message) {
        MessageObj messageObj;
        if (message.getFrom() == null) {
            messageObj = new TextMessage(message.getStanzaId(), message.getTo().asBareJid().toString(), Constants.FROM, State.SENT, message.getBody(), System.currentTimeMillis());
            int index = messageObjList.indexOf(messageObj);
            messageObjList.get(index).setState(State.SENT);
        } else {
            messageObj = new TextMessage(message.getStanzaId(), message.getTo().asBareJid().toString(), message.getFrom().asBareJid().toString(), State.RECEIVED, message.getBody(), System.currentTimeMillis());
            messageObjList.add(messageObj);
        }
        EventBus.getDefault().post(new MessageEvent(message));
    }

}

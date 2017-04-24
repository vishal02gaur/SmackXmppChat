package vishal.chatdemo.managers;

import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import vishal.chatdemo.BuildConfig;
import vishal.chatdemo.Constants;
import vishal.chatdemo.State;
import vishal.chatdemo.messages.MessageObj;
import vishal.chatdemo.messages.TextMessage;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public class MessageManager implements XMPPConnectionManager.OnMessageReceiveListener {
    private static String TAG = MessageManager.class.getSimpleName();
    private BlockingQueue<MessageObj> mMessageObjQueue;
    private List<MessageObj> messageObjList;
    private Thread thread;
    private static MessageManager messageManager;

    public static MessageManager getMessageManager() {
        if (messageManager == null)
            messageManager = new MessageManager();
        return messageManager;
    }

    private MessageManager() {
        mMessageObjQueue = new LinkedBlockingQueue<MessageObj>();
        messageObjList = new ArrayList<>();
        XMPPConnectionManager.getXmppConnectionManager().setListener(this);
        thread = new Thread(queueController);
        thread.start();
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
        message.setType(Message.Type.normal);
        message.setBody(messageObj.getMsg());

        try {
            boolean isSend = XMPPConnectionManager.getXmppConnectionManager().sendMessage(message);
            if (isSend == true)
                messageObj.setState(State.SENT);
            else
                messageObj.setState(State.FAILED);

        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
            messageObj.setState(State.FAILED);
        } catch (InterruptedException e) {
            e.printStackTrace();
            messageObj.setState(State.FAILED);
        }

    }

    @Override
    public void onMessageReceived(Message message) {
        MessageObj messageObj = new TextMessage(message.getStanzaId(), message.getTo().asBareJid().toString(), message.getFrom().asBareJid().toString(), State.RECEIVED, message.getBody(), System.currentTimeMillis());
        //int index = messageObjList.indexOf(messageObj);
        messageObjList.add(messageObj);
    }


}

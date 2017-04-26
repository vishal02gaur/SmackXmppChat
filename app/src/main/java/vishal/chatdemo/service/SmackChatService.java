package vishal.chatdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

import vishal.chatdemo.Constants;
import vishal.chatdemo.managers.XMPPConnectionManager;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 25/4/17
 */

public class SmackChatService extends Service {


    private XMPPConnectionManager xmppConnectionManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return START_STICKY;
    }

    public void start() throws IOException, InterruptedException, SmackException, XMPPException {
        XMPPConnectionManager.initXMPPConnectionManager(Constants.USERNAME, "test123");
        xmppConnectionManager = XMPPConnectionManager.getXmppConnectionManager();
        xmppConnectionManager.connect();
    }
}

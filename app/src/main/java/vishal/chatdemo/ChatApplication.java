package vishal.chatdemo;

import android.app.Application;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

import vishal.chatdemo.managers.XMPPConnectionManager;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public class ChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    XMPPConnectionManager.initXMPPConnectionManager(Constants.USERNAME, "test123");
                    XMPPConnectionManager.getXmppConnectionManager().connect();
                } catch (XmppStringprepException e) {
                    e.printStackTrace();
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
    }
}

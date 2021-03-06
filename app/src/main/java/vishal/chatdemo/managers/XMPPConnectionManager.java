package vishal.chatdemo.managers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.PresenceEventListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntries;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.roster.SubscribeListener;
import org.jivesoftware.smack.roster.provider.RosterPacketProvider;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.chatstates.ChatStateManager;
import org.jivesoftware.smackx.chatstates.packet.ChatStateExtension;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.iqlast.packet.LastActivity;
import org.jivesoftware.smackx.muc.MUCAffiliation;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.receipts.DeliveryReceipt;
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptRequest;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import vishal.chatdemo.Constants;
import vishal.chatdemo.events.ReceiptEvent;
import vishal.chatdemo.messages.ext.MessageExtension;


/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public class XMPPConnectionManager implements IncomingChatMessageListener, StanzaListener {

    private final String TAG = XMPPConnectionManager.class.getSimpleName();

    private static XMPPTCPConnectionConfiguration xmppConfig;

    private static XMPPConnectionManager xmppConnectionManager;

    private XMPPTCPConnection connection;
    private ConnectionListener connectionListener;

    @Override
    public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException {
        if (packet instanceof Message) {
            Log.d(TAG, "RECEIVED MESSAGE............");
            Log.d(TAG, packet.toXML().toString());
            EventBus.getDefault().post((Message) packet);
        }
    }

    public static void initXMPPConnectionManager(String username, String password) throws XmppStringprepException {

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(Constants.HOST_IP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        xmppConfig = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(username, password)
                .setXmppDomain(Constants.XMPP_DOMAIN)
                .setKeystoreType(null)
                .setDebuggerEnabled(true)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setHost(Constants.HOST_IP)
                .setHostAddress(inetAddress)
                .setCompressionEnabled(false)
                .setPort(5222)
                .build();

    }

    private XMPPConnectionManager() {
        connectionListener = new ConnectionListener();
        connection = new XMPPTCPConnection(xmppConfig);
        connection.addConnectionListener(connectionListener);
        connection.addStanzaAcknowledgedListener(this);
        connection.addAsyncStanzaListener(this, new StanzaFilter() {
            @Override
            public boolean accept(Stanza stanza) {
                if (stanza instanceof Message)
                    return true;
                return false;
            }
        });
        connection.setPacketReplyTimeout(10000);
        ReconnectionManager.getInstanceFor(connection).enableAutomaticReconnection();

        ProviderManager.addExtensionProvider(MessageExtension.ELEMENT, MessageExtension.NAMESPACE, new MessageExtension.Provider());


    }

    public static XMPPConnectionManager getXmppConnectionManager() {
        if (xmppConnectionManager == null)
            xmppConnectionManager = new XMPPConnectionManager();
        return xmppConnectionManager;
    }


    public void connect() throws InterruptedException, XMPPException, SmackException, IOException {
        connection.connect();
        connection.login();
     /*   chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addIncomingListener(this);*/

    }

    void sendMessage(Message message) throws IOException, SmackException, InterruptedException, XMPPException {
        if (!connection.isConnected()) {
            connect();
        }
        if (connection.isConnected() && connection.isAuthenticated()) {
            DeliveryReceiptRequest.addTo(message);
            connection.sendStanza(message);
        }
    }


    public void disconnect() {
        connection.disconnect();
    }

    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
        Log.d(TAG, "RECEIVED MESSAGE............");
        Log.d(TAG, message.toXML().toString());
    }


    class ConnectionListener implements org.jivesoftware.smack.ConnectionListener {
        @Override
        public void connected(XMPPConnection mconnection) {
            Log.d(TAG, "connected");
            ProviderManager.addExtensionProvider(DeliveryReceipt.ELEMENT, DeliveryReceipt.NAMESPACE, new DeliveryReceipt.Provider());
            ProviderManager.addExtensionProvider(DeliveryReceiptRequest.ELEMENT, new DeliveryReceiptRequest().getNamespace(), new DeliveryReceiptRequest.Provider());

            DeliveryReceiptManager.getInstanceFor(connection).addReceiptReceivedListener(new ReceiptReceivedListener() {
                @Override
                public void onReceiptReceived(Jid fromJid, Jid toJid, String receiptId, Stanza receipt) {
                    Log.d(TAG, fromJid.toString());
                    Log.d(TAG, toJid.toString());
                    Log.d(TAG, "PACKED GOT--" + receiptId);
                    EventBus.getDefault().post(new ReceiptEvent(fromJid, toJid, receiptId, receipt));

                }
            });

            ChatStateManager.getInstance(connection);
            DeliveryReceiptManager.getInstanceFor(connection).autoAddDeliveryReceiptRequests();
            Roster roster = Roster.getInstanceFor(connection);
            roster.addPresenceEventListener(PresenceManager.getPresenceManager());
            roster.addRosterListener(PresenceManager.getPresenceManager());
            roster.addRosterLoadedListener(PresenceManager.getPresenceManager());
            roster.addSubscribeListener(new SubscribeListener() {
                @Override
                public SubscribeAnswer processSubscribe(Jid from, Presence subscribeRequest) {
                    if (subscribeRequest.getType() == Presence.Type.subscribe) {
                        return SubscribeAnswer.Approve;
                    }
                    return null;
                }
            });
            ServiceDiscoveryManager discoveryManager = ServiceDiscoveryManager.getInstanceFor(connection);;
            List<String> services =  discoveryManager.getFeatures();

            for(String service:services){
                Log.d(TAG,service);
            }
        }

        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {
            Log.d(TAG, "authenticated");
        }

        @Override
        public void connectionClosed() {
            Log.d(TAG, "connectionClosed");
        }

        @Override
        public void connectionClosedOnError(Exception e) {
            Log.d(TAG, "connectionClosedOnError");
        }

        @Override
        public void reconnectionSuccessful() {
            Log.d(TAG, "reconnectionSuccessful");
        }

        @Override
        public void reconnectingIn(int seconds) {
            Log.d(TAG, "reconnectingIn");
        }

        @Override
        public void reconnectionFailed(Exception e) {
            Log.d(TAG, "reconnectionFailed");
        }
    }

}

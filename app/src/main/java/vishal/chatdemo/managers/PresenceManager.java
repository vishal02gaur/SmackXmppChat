package vishal.chatdemo.managers;

import android.util.Log;

import org.jivesoftware.smack.PresenceListener;
import org.jivesoftware.smack.filter.PresenceTypeFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.PresenceEventListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.roster.RosterLoadedListener;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.FullJid;
import org.jxmpp.jid.Jid;

import java.util.Collection;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 27/4/17
 */

public class PresenceManager implements PresenceEventListener, PresenceListener ,RosterListener,RosterLoadedListener {
    private final String TAG = PresenceManager.class.getSimpleName();

    private static PresenceManager presenceManager;

    public static PresenceManager getPresenceManager() {
        if (presenceManager == null)
            presenceManager = new PresenceManager();
        return presenceManager;
    }

    @Override
    public void presenceAvailable(FullJid address, Presence availablePresence) {
        Log.d(TAG,"presenceAvailable");
    }

    @Override
    public void presenceUnavailable(FullJid address, Presence presence) {
        Log.d(TAG,"presenceUnavailable");
    }

    @Override
    public void presenceError(Jid address, Presence errorPresence) {
        Log.d(TAG,"presenceError");
    }

    @Override
    public void presenceSubscribed(BareJid address, Presence subscribedPresence) {
        Log.d(TAG,"presenceSubscribed");
    }

    @Override
    public void presenceUnsubscribed(BareJid address, Presence unsubscribedPresence) {
        Log.d(TAG,"presenceUnsubscribed");
    }

    @Override
    public void processPresence(Presence presence) {
        Log.d(TAG,"processPresence");
    }

    @Override
    public void entriesAdded(Collection<Jid> addresses) {
        Log.d(TAG,"entriesAdded");
    }

    @Override
    public void entriesUpdated(Collection<Jid> addresses) {
        Log.d(TAG,"entriesUpdated");
    }

    @Override
    public void entriesDeleted(Collection<Jid> addresses) {
        Log.d(TAG,"entriesDeleted");
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.d(TAG,"presenceChanged");
    }

    @Override
    public void onRosterLoaded(Roster roster) {
        Log.d(TAG,"onRosterLoaded");
    }

    @Override
    public void onRosterLoadingFailed(Exception exception) {
        Log.d(TAG,"onRosterLoadingFailed");
    }
}

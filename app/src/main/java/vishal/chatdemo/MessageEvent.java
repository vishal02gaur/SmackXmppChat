package vishal.chatdemo;

import org.jivesoftware.smack.packet.Stanza;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 26/4/17
 */

public class MessageEvent {
    private Stanza stanza;

    public MessageEvent(Stanza stanza) {
        this.stanza = stanza;
    }

    public Stanza getStanza() {
        return stanza;
    }
}

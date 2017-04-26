package vishal.chatdemo.events;

import org.jivesoftware.smack.packet.Stanza;

import vishal.chatdemo.messages.MessageObj;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 26/4/17
 */

public class MessageEvent {
    private MessageObj messageObj;

    public MessageEvent(MessageObj messageObj) {
        this.messageObj = messageObj;
    }

    public MessageObj getStanza() {
        return messageObj;
    }
}

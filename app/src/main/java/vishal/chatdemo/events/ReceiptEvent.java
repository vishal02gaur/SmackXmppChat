package vishal.chatdemo.events;

import org.jivesoftware.smack.packet.Stanza;
import org.jxmpp.jid.Jid;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 28/4/17
 */

public class ReceiptEvent {
    private Jid fromJid;
    private Jid toJid;
    private String receiptId;
    private Stanza receipt;

    public ReceiptEvent(Jid fromJid, Jid toJid, String receiptId, Stanza receipt) {
        this.fromJid = fromJid;
        this.toJid = toJid;
        this.receiptId = receiptId;
        this.receipt = receipt;
    }

    public Jid getFromJid() {
        return fromJid;
    }

    public Jid getToJid() {
        return toJid;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public Stanza getReceipt() {
        return receipt;
    }
}

package vishal.chatdemo.messages.ext;

import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.Map;

import vishal.chatdemo.messages.ext.MessageExtension;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 26/4/17
 */

public class TextMessageExt extends MessageExtension {
    public TextMessageExt() {

    }

    @Override
    public String getType() {
        return MessageType.MSG_PLAIN;
    }

    @Override
    protected void composeXml(XmlStringBuilder xml) {

    }

    @Override
    protected void setAttribute(Map<String, String> attributeMap) {

    }


}

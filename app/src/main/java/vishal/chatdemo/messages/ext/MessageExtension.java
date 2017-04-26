package vishal.chatdemo.messages.ext;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 26/4/17
 */

public abstract class MessageExtension implements ExtensionElement {
    public static final String NAMESPACE = "custom:chat";
    public static final String ELEMENT = "chat";

    public static final String MESSAGE_TYPE = "type";

    public MessageExtension() {
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    @Override
    public CharSequence toXML() {
        XmlStringBuilder xml = new XmlStringBuilder(this);
        xml.attribute(MESSAGE_TYPE, getType());
        composeXml(xml);
        xml.closeEmptyElement();
        return xml;
    }

    public abstract String getType();

    protected abstract void composeXml(XmlStringBuilder xml);

    protected abstract void setAttribute(Map<String, String> attributeMap);


    public static class Provider extends EmbeddedExtensionProvider<MessageExtension> {
        @Override
        protected MessageExtension createReturnExtension(String currentElement, String currentNamespace, Map<String, String> attributeMap, List<? extends ExtensionElement> content) {
            String msgType = attributeMap.get(MESSAGE_TYPE);
            MessageExtension messageExt = null;
            switch (msgType) {
                case MessageType.MSG_PLAIN:
                    messageExt = new TextMessageExt();
                    break;
                case MessageType.MSG_IMAGE:
                    messageExt = new ImageMessageExt();
                    break;
            }
            messageExt.setAttribute(attributeMap);
            return messageExt;
        }
    }

}

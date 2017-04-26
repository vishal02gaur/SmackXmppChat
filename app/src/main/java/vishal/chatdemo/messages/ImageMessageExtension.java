package vishal.chatdemo.messages;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 25/4/17
 */

public class ImageMessageExtension extends CustomMessage {

    @Override
    public CharSequence toXML() {
        XmlStringBuilder xml = new XmlStringBuilder(this);
        xml.openElement("gasdas");
        //xml.attribute(ATTRIBUTE_REPLY_TEXT, getReplyText());
        xml.closeElement("gasdas");
        return xml;
    }

    public static class Provider extends EmbeddedExtensionProvider<ImageMessageExtension> {
        @Override
        protected ImageMessageExtension createReturnExtension(String currentElement, String currentNamespace, Map<String, String> attributeMap, List<? extends ExtensionElement> content) {
            ImageMessageExtension imageMessageExtension = new ImageMessageExtension();
            //  repExt.setReplyText(attributeMap.get(ATTRIBUTE_REPLY_TEXT));
            return imageMessageExtension;
        }
    }
}

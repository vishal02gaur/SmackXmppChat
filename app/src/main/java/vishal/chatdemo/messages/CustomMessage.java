package vishal.chatdemo.messages;

import org.jivesoftware.smack.packet.ExtensionElement;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 25/4/17
 */

public abstract class CustomMessage implements ExtensionElement {
    public static final String NAMESPACE = "custom:chat";
    public static final String ELEMENT = "chat";


    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }

}

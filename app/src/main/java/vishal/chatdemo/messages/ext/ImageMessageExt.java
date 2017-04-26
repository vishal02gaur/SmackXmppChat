package vishal.chatdemo.messages.ext;

import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.Map;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 26/4/17
 */

public class ImageMessageExt extends MessageExtension {

    private String imageUrl = null;

    static final String ATTRIBUTE_IMAGE_URL = "_hres_url";

    public ImageMessageExt() {

    }

    @Override
    public String getType() {
        return MessageType.MSG_IMAGE;
    }

    @Override
    protected void composeXml(XmlStringBuilder xml) {
        xml.attribute(ATTRIBUTE_IMAGE_URL, getImageUrl());
    }

    @Override
    protected void setAttribute(Map<String, String> attributeMap) {
        setImageUrl(attributeMap.get(ATTRIBUTE_IMAGE_URL));
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

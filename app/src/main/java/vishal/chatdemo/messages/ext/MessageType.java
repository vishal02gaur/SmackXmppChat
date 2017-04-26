package vishal.chatdemo.messages.ext;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 26/4/17
 */

@StringDef({MessageType.MSG_PLAIN,MessageType.MSG_IMAGE})
@Retention(RetentionPolicy.SOURCE)
@interface MessageType {
    String MSG_PLAIN = "text";
    String MSG_IMAGE = "image";
}

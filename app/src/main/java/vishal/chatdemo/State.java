package vishal.chatdemo;

import android.support.annotation.IntegerRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */
@IntDef({State.SENDING, State.SENT, State.FAILED})
@Retention(RetentionPolicy.SOURCE)
public @interface State {
    int SENDING = 1;
    int SENT = 2;
    int FAILED = 3;
}

package vishal.chatdemo.messages;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public class TextMessage extends MessageObj {
    private String msg;
    private long time;

    public TextMessage(String _id, String _to, String _from, int state, String msg, long time) {
        super(_id, _to, _from, state);
        this.msg = msg;
        this.time = time;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public long getTime() {
        return time;
    }


}

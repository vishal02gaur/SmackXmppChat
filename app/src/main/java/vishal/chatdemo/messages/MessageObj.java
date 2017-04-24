package vishal.chatdemo.messages;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public abstract class MessageObj {
    private String _id;
    protected String _to;
    protected String _from;
    protected int state;

    public MessageObj(String _id, String _to, String _from, int state) {
        this._id = _id;
        this._to = _to;
        this._from = _from;
        this.state = state;
    }

    public String get_id() {
        return _id;
    }

    public String get_to() {
        return _to;
    }

    public String get_from() {
        return _from;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg(){
        return "";
    }
}

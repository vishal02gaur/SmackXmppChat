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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageObj that = (MessageObj) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (_to != null ? !_to.equals(that._to) : that._to != null) return false;
        return _from != null ? _from.equals(that._from) : that._from == null;

    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (_to != null ? _to.hashCode() : 0);
        result = 31 * result + (_from != null ? _from.hashCode() : 0);
        return result;
    }
}

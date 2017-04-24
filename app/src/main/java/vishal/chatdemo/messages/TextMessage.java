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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextMessage that = (TextMessage) o;

        if (time != that.time) return false;
        if (msg != null ? !msg.equals(that.msg) : that.msg != null) return false;
        if (_to != null ? !_to.equals(that._to) : that._to != null) return false;
        return _from != null ? _from.equals(that._from) : that._from == null;

    }

    @Override
    public int hashCode() {
        int result = msg != null ? msg.hashCode() : 0;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (_to != null ? _to.hashCode() : 0);
        result = 31 * result + (_from != null ? _from.hashCode() : 0);
        return result;
    }
}

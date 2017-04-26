package vishal.chatdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vishal.chatdemo.Constants;
import vishal.chatdemo.R;
import vishal.chatdemo.State;
import vishal.chatdemo.messages.MessageObj;

/**
 * Author : Vishal Gaur
 * Email  : vishal02gaur@gmail.com
 * Date   : 24/4/17
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<MessageObj> list;
    private OnChatClickListener clickListener;


    public interface OnChatClickListener {
        public void onRetry(MessageObj messageObj);
    }

    public ChatAdapter(List<MessageObj> list) {
        this.list = list;
    }

    public void setClickListener(OnChatClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        ViewHolder viewHolder = null;
        switch (viewType) {
            case R.layout.receive_layout:
                viewHolder = new ReceiveHolder(view);
                break;
            case R.layout.send_layout:
                viewHolder = new SendViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        MessageObj messageObj = list.get(position);
        if (messageObj.get_from().equals(Constants.FROM)) {
            return R.layout.send_layout;
        } else {
            return R.layout.receive_layout;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBind(MessageObj messageObj);

    }

    class SendViewHolder extends ViewHolder implements View.OnClickListener {

        TextView msgView;
        TextView statusView;

        public SendViewHolder(View itemView) {
            super(itemView);
            msgView = (TextView) itemView.findViewById(R.id.msg);
            statusView = (TextView) itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(MessageObj messageObj) {
            msgView.setText(messageObj.getMsg());
            switch (messageObj.getState()) {
                case State.SENDING:
                    statusView.setText("SENDING");
                    break;
                case State.FAILED:
                    statusView.setText("FAILED");
                    break;
                case State.SENT:
                    statusView.setText("SENT");
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            MessageObj messageObj = list.get(getAdapterPosition());
            switch (messageObj.getState()) {
                case State.FAILED:
                    if (clickListener != null)
                        clickListener.onRetry(messageObj);
                    break;
            }

        }
    }

    class ReceiveHolder extends ViewHolder {

        TextView msgView;

        public ReceiveHolder(View itemView) {
            super(itemView);
            msgView = (TextView) itemView.findViewById(R.id.msg);
        }

        @Override
        public void onBind(MessageObj messageObj) {
            msgView.setText(messageObj.getMsg());
        }
    }
}

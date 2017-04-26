package vishal.chatdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.packet.Message;

import vishal.chatdemo.adapters.ChatAdapter;
import vishal.chatdemo.managers.ChatMessageManager;
import vishal.chatdemo.messages.MessageObj;
import vishal.chatdemo.messages.TextMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,ChatAdapter.OnChatClickListener {


    private RecyclerView mChatRecyclerView;
    private EditText mChatEditTextView;
    private Button mChatSend;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChatRecyclerView = (RecyclerView) findViewById(R.id.chat_list);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        mChatRecyclerView.setLayoutManager(linearLayout);
        chatAdapter = new ChatAdapter(ChatMessageManager.getMessageManager().getMessageObjList());

        mChatRecyclerView.setAdapter(chatAdapter);

        mChatEditTextView = (EditText) findViewById(R.id.chat);
        mChatSend = (Button) findViewById(R.id.send);

        mChatSend.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        chatAdapter.setClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        chatAdapter.setClickListener(null);
    }

    private void sendMessage(String msg) {
        String id = "_id_" + System.currentTimeMillis();
        MessageObj messageObj = new TextMessage(id, Constants.TO, Constants.FROM, State.SENDING, msg, System.currentTimeMillis());
        ChatMessageManager.getMessageManager().addMessage(messageObj);
        chatAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                String msg = mChatEditTextView.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    mChatEditTextView.setText("");
                    sendMessage(msg);
                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(MessageEvent messageEvent) {
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRetry(MessageObj messageObj) {
        messageObj.setState(State.SENDING);
        ChatMessageManager.getMessageManager().retry(messageObj);
        chatAdapter.notifyDataSetChanged();
    }
}

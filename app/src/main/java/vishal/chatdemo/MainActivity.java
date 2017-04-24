package vishal.chatdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

import vishal.chatdemo.managers.MessageManager;
import vishal.chatdemo.managers.XMPPConnectionManager;
import vishal.chatdemo.messages.MessageObj;
import vishal.chatdemo.messages.TextMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView mChatRecyclerView;
    private EditText mChatEditTextView;
    private Button mChatSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChatRecyclerView = (RecyclerView) findViewById(R.id.chat_list);
        mChatEditTextView = (EditText) findViewById(R.id.chat);
        mChatSend = (Button) findViewById(R.id.send);

        mChatSend.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void sendMessage(String msg) {
        String id = "_id_" + System.currentTimeMillis();
        MessageObj messageObj = new TextMessage(id, Constants.TO, Constants.FROM, State.SENDING, msg, System.currentTimeMillis());
        MessageManager.getMessageManager().addMessage(messageObj);

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
}

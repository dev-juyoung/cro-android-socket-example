package xyz.cro.croandroidsocketexample.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import xyz.cro.croandroidsocketexample.R;
import xyz.cro.croandroidsocketexample.bases.BaseActivity;
import xyz.cro.croandroidsocketexample.bases.Constants;
import xyz.cro.croandroidsocketexample.models.ChatMessage;
import xyz.cro.croandroidsocketexample.utils.Dlog;

public class ChatActivity extends BaseActivity {
    @BindView(R.id.messageEditText)
    AppCompatEditText messageEditText;
    @BindView(R.id.sendButton)
    AppCompatButton sendButton;

    @BindColor(R.color.textEnabled)
    int enableTextColor;
    @BindColor(R.color.textDisabled)
    int disableTextColor;

    private Socket mSocket;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getIntentData();
    }

    private void getIntentData() {
        if (getIntent() == null) {
            finish();
            return;
        }

        userName = getIntent().getStringExtra(Constants.EXTRA_USERNAME);
        Dlog.d("received username: " + userName);

        initializeView();
        setupSocketClient();
    }

    private void initializeView() {
        getSupportActionBar().setTitle("Cro 채팅방");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendButton.setEnabled(false);
        sendButton.setTextColor(disableTextColor);
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    sendButton.setEnabled(true);
                    sendButton.setTextColor(enableTextColor);
                } else {
                    sendButton.setEnabled(false);
                    sendButton.setTextColor(disableTextColor);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void setupSocketClient() {
        try {
            mSocket = IO.socket(Constants.SOCKET_URL);
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Constants.EVENT_SYSTEM, onMessageReceived);
            mSocket.on(Constants.EVENT_MESSAGE, onMessageReceived);

            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Socket Server 연결 Listener
     */
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 서버로 전송할 데이터 생성 및 채널 입장 이벤트 보냄.
            JSONObject sendData = new JSONObject();
            try {
                sendData.put(Constants.SEND_DATA_USERNAME, userName);
                mSocket.emit(Constants.EVENT_ENTERED, sendData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Message 전달 Listener
     */
    private Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject rcvData = (JSONObject) args[0];
            String userAction = rcvData.optString("action");
            String messageType = rcvData.optString("type");
            String messageOwner = rcvData.optJSONObject("data").optString("username");
            String messageContent = rcvData.optJSONObject("data").optString("message");

            ChatMessage message = new ChatMessage(userAction, messageType, messageOwner, messageContent);
            Dlog.d("action: " + message.getUserAction());
            Dlog.d("type: " + message.getMessageType());
            Dlog.d("owner: " + message.getMessageOwner());
            Dlog.d("message: " + message.getMessageContent());
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.sendButton)
    void sendButtonTouchUp() {
        String sendMessage = messageEditText.getText().toString();
        Dlog.d("message: " + sendMessage);
    }
}

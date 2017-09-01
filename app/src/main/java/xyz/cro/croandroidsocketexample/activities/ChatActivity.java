package xyz.cro.croandroidsocketexample.activities;

import android.os.Bundle;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import xyz.cro.croandroidsocketexample.R;
import xyz.cro.croandroidsocketexample.bases.BaseActivity;
import xyz.cro.croandroidsocketexample.bases.Constants;
import xyz.cro.croandroidsocketexample.utils.Dlog;

public class ChatActivity extends BaseActivity {
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
    }

    private void setupSocketClient() {
        try {
            mSocket = IO.socket(Constants.SOCKET_URL);
            mSocket.on(Socket.EVENT_CONNECT, onConnect);

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
}

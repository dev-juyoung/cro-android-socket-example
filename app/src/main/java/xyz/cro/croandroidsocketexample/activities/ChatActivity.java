package xyz.cro.croandroidsocketexample.activities;

import android.os.Bundle;
import android.view.MenuItem;

import xyz.cro.croandroidsocketexample.R;
import xyz.cro.croandroidsocketexample.bases.BaseActivity;
import xyz.cro.croandroidsocketexample.bases.Constants;
import xyz.cro.croandroidsocketexample.utils.Dlog;

public class ChatActivity extends BaseActivity {

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getIntentData();
        initializeView();
    }

    private void getIntentData() {
        if (getIntent() == null) {
            finish();
            return;
        }

        userName = getIntent().getStringExtra(Constants.EXTRA_USERNAME);
        Dlog.d("received username: " + userName);
    }

    private void initializeView() {
        getSupportActionBar().setTitle("Cro 채팅방");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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

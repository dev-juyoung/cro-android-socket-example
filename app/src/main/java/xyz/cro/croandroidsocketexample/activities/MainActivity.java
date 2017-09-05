package xyz.cro.croandroidsocketexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import xyz.cro.croandroidsocketexample.R;
import xyz.cro.croandroidsocketexample.bases.BaseActivity;
import xyz.cro.croandroidsocketexample.bases.Constants;

public class MainActivity extends BaseActivity {
    @BindView(R.id.usernameInputLayout)
    TextInputLayout usernameInputLayout;
    @BindView(R.id.usernameEditText)
    AppCompatEditText usernameEditText;
    @BindView(R.id.enteredButton)
    AppCompatButton enteredButton;

    @BindColor(R.color.colorWhite)
    int toolbarTitleColor;
    @BindColor(R.color.textEnabled)
    int enableTextColor;
    @BindColor(R.color.textDisabled)
    int disableTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.BrownTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
    }

    private void initializeView() {
        getToolbar().setTitleTextColor(toolbarTitleColor);
        getSupportActionBar().setTitle("CroAndroidSocketExample");

        usernameInputLayout.setErrorEnabled(false);
        enteredButton.setEnabled(false);
        enteredButton.setTextColor(disableTextColor);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    enteredButton.setEnabled(true);
                    enteredButton.setTextColor(enableTextColor);
                } else {
                    enteredButton.setEnabled(false);
                    enteredButton.setTextColor(disableTextColor);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @OnClick(R.id.enteredButton)
    void enteredButtonTouchUp() {
        String userName = usernameEditText.getText().toString();

        Intent intent = new Intent(this, ChatMessageActivity.class);
        intent.putExtra(Constants.EXTRA_USERNAME, userName);
        startActivity(intent);

        // 기존에 입력된 닉네임 초기화
        usernameEditText.setText(null);
    }
}

package xyz.cro.croandroidsocketexample.bases;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.cro.croandroidsocketexample.R;

/**
 * Created by juyounglee on 2017. 9. 1..
 */

public class BaseActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_base)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    public Toolbar getToolbar() {
        if (toolbar == null) {
            throw new NullPointerException("toolbar was not ready.");
        }

        return toolbar;
    }
}

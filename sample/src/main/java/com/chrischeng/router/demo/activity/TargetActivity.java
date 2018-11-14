package com.chrischeng.router.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.annotation.RouterAction;
import com.chrischeng.router.demo.R;
import com.chrischeng.router.demo.interceptor.TargetActivityInterceptor;

import java.util.Locale;

@RouterInterceptor(TargetActivityInterceptor.class)
@RouterAction("target")
public class TargetActivity extends AppCompatActivity {

    static final String KEY_YEAR = "year";
    static final String KEY_MONTH = "month";
    static final String KEY_DAY = "day";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;

        String param = String.format(Locale.getDefault(), "%1s.%2s.%3s", bundle.get(KEY_YEAR), bundle.get(KEY_MONTH), bundle.get(KEY_DAY));
        ((TextView) findViewById(R.id.tv_param)).setText(param);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(Activity.RESULT_OK);
    }
}

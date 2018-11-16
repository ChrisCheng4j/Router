package com.chrischeng.router.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chrischeng.parceler.annotation.ParcelerArg;
import com.chrischeng.router.annotation.RouterAction;
import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.demo.R;
import com.chrischeng.router.demo.interceptor.LoginInterceptor;
import com.chrischeng.router.demo.model.User;

import java.util.Locale;

@RouterInterceptor(LoginInterceptor.class)
@RouterAction("target")
public class TargetActivity extends BaseActivity {

    static final String KEY_DATE = "date";
    static final String KEY_USER = "user";

    @ParcelerArg
    String date;
    @ParcelerArg
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        ((TextView) findViewById(R.id.tv_date)).setText(date);
        ((TextView) findViewById(R.id.tv_user)).setText(String.format(Locale.getDefault(), "%s %d", user.name, user.age));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(Activity.RESULT_OK);
    }
}

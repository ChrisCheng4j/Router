package com.chrischeng.router.demo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chrischeng.parceler.annotation.ParcelerArg;
import com.chrischeng.router.Router;
import com.chrischeng.router.annotation.RouterAction;
import com.chrischeng.router.demo.App;
import com.chrischeng.router.demo.R;
import com.chrischeng.router.model.RouterTargetBundle;

@RouterAction("login")
public class LoginActivity extends BaseActivity {

    public static final String KEY_URI = "uri";
    public static final String KEY_BUNDLE = "bundle";

    @ParcelerArg
    Uri uri;
    @ParcelerArg
    RouterTargetBundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin();
            }
        });
    }

    private void onLogin() {
        App.login();
        Router.resume(uri, bundle).route(this);
        finish();
    }
}

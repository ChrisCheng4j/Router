package com.chrischeng.router.demo.interceptor;

import android.content.Context;
import android.net.Uri;

import com.chrischeng.router.Router;
import com.chrischeng.router.demo.App;
import com.chrischeng.router.demo.activity.LoginActivity;
import com.chrischeng.router.interceptor.RouterInterceptor;
import com.chrischeng.router.model.RouterTargetBundle;

public class LoginInterceptor implements RouterInterceptor {

    @Override
    public boolean intercept(Context context, Uri uri) {
        return !App.isLogin;
    }

    @Override
    public void onIntercepted(Context context, Uri uri, RouterTargetBundle targetBundle) {
        Router.createUriBuilder("login")
                .withParam(LoginActivity.KEY_URI, uri)
                .withParam(LoginActivity.KEY_BUNDLE, targetBundle)
                .build()
                .route(context);
    }
}

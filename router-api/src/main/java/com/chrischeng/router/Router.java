package com.chrischeng.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.chrischeng.router.callback.RouteCallback;
import com.chrischeng.router.interceptor.RouterInterceptor;
import com.chrischeng.router.launcher.RouteActivityLauncher;

public final class Router {

    private Uri uri;
    private RouteTargetBundle targetBundle;

    public static void init(Context context) {
        RouteManager.getInstance().init(context);
    }

    private Router(Uri uri) {
        this.uri = uri;
        this.targetBundle = new RouteTargetBundle();
    }

    public static Router create(String url) {
        return new Router(Uri.parse(url));
    }

    public Router addFlags(int flags) {
        targetBundle.flags = flags;
        return this;
    }

    public Router putExtras(Bundle extras) {
        targetBundle.extras = extras;
        return this;
    }

    public Router requestCode(int requestCode) {
        targetBundle.requestCode = requestCode;
        return this;
    }

    public Router addOptions(Bundle options) {
        targetBundle.options = options;
        return this;
    }

    public Router overridePendingTransition(int enterAnim, int exitAnim) {
        targetBundle.enterAnim = enterAnim;
        targetBundle.exitAnim = exitAnim;
        return this;
    }

    public Router addInterceptor(Class<? extends RouterInterceptor> interceptor) {
        targetBundle.addInterceptors(interceptor);
        return this;
    }

    public Router setCallback(RouteCallback callback) {
        targetBundle.callback = callback;
        return this;
    }

    public void route() {
        route(null);
    }

    public void route(Context context) {
        RouteActivityLauncher.getInstance().launchActivity(context, uri, targetBundle);
    }
}

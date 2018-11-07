package com.chrischeng.router.callback;

import android.net.Uri;

import com.chrischeng.router.rule.RouterRule;

public interface RouteCallback {

    void onSuccess(Uri uri, RouterRule rule);

    void onFailed(Uri uri, Throwable e);
}

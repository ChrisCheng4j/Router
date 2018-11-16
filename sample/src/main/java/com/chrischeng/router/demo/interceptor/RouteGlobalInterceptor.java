package com.chrischeng.router.demo.interceptor;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.chrischeng.router.model.RouterTargetBundle;
import com.chrischeng.router.interceptor.RouterInterceptor;

public class RouteGlobalInterceptor implements RouterInterceptor {

    @Override
    public boolean intercept(Context context, Uri uri) {
        return false;
    }

    @Override
    public void onIntercepted(Context context, Uri uri, RouterTargetBundle targetBundle) {
        Log.d("aaa", getClass().getSimpleName() + " onIntercepted");
    }
}

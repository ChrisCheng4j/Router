package com.chrischeng.router.demo.interceptor;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.chrischeng.router.model.RouteTargetBundle;
import com.chrischeng.router.interceptor.RouterInterceptor;

public class TargetActivityInterceptor implements RouterInterceptor {

    @Override
    public boolean intercept(Context context, Uri uri) {
        return false;
    }

    @Override
    public void onIntercepted(Context context, Uri uri, RouteTargetBundle targetBundle) {
        Log.d("aaa", getClass().getSimpleName() + " onIntercepted");
    }
}

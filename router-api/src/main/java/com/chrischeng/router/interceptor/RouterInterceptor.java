package com.chrischeng.router.interceptor;

import android.content.Context;
import android.net.Uri;

import com.chrischeng.router.model.RouteTargetBundle;

public interface RouterInterceptor {

    boolean intercept(Context context, Uri uri);

    void onIntercepted(Context context, Uri uri, RouteTargetBundle targetBundle);
}

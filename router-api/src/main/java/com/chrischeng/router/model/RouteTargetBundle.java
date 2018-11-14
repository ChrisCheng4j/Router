package com.chrischeng.router.model;

import android.os.Bundle;

import com.chrischeng.router.callback.RouterCallback;
import com.chrischeng.router.interceptor.RouterInterceptor;

import java.util.ArrayList;
import java.util.List;

public final class RouteTargetBundle {

    public int flags;
    public Bundle extras;
    public Bundle options;
    public int requestCode = -1;
    public int enterAnim = -1, exitAnim = -1;
    public RouterCallback callback;
    public List<Class<? extends RouterInterceptor>> interceptors;

    public void addInterceptors(Class<? extends RouterInterceptor> interceptor) {
        if (interceptors == null)
            interceptors = new ArrayList<>();
        interceptors.add(interceptor);
    }

    public void removeAllInterceptors() {
        if (interceptors != null)
            interceptors.clear();
    }
}

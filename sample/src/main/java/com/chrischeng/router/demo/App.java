package com.chrischeng.router.demo;

import android.app.Application;

import com.chrischeng.router.Router;
import com.chrischeng.router.annotation.RouterConfig;
import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.demo.interceptor.RouteGlobalInterceptor;

@RouterConfig(pkg = "com.chrischeng.router.demo", globalInterceptors = @RouterInterceptor(RouteGlobalInterceptor.class))
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
        Router.setGlobalRouteScheme("router");
    }
}

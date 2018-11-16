package com.chrischeng.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.chrischeng.parceler.api.converter.ParcelJsonConverter;
import com.chrischeng.router.callback.RouterCallback;
import com.chrischeng.router.interceptor.RouterInterceptor;
import com.chrischeng.router.launcher.RouteActivityLauncher;
import com.chrischeng.router.manager.RouteManager;
import com.chrischeng.router.model.RouterTargetBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Router {

    private Uri uri;
    private RouterTargetBundle targetBundle;

    private Router(Uri uri) {
        this.uri = uri;
        this.targetBundle = new RouterTargetBundle();
    }

    public static void init(Context context) {
        RouteManager.getInstance().init(context);
    }

    public static void setGlobalRouteScheme(String routeScheme) {
        RouteManager.getInstance().setGlobalRouteScheme(routeScheme);
    }

    public static Router resume(Uri uri, RouterTargetBundle bundle) {
        Router router = Router.create(uri.toString());
        router.targetBundle = bundle;
        return router;
    }

    public static Router create(String uri) {
        return new Router(Uri.parse(RouteManager.getInstance().getCompleteUri(uri)));
    }

    public static UriBuilder createUriBuilder(String route) {
        return new UriBuilder(route);
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

    public Router setCallback(RouterCallback callback) {
        targetBundle.callback = callback;
        return this;
    }

    public void route() {
        route(null);
    }

    public void route(Context context) {
        RouteActivityLauncher.getInstance().launchActivity(context, uri, targetBundle);
    }

    public static class UriBuilder {

        private String route;
        private String scheme;
        private Map<String, String> params;

        public UriBuilder(String route) {
            this.route = route;
        }

        public UriBuilder withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public UriBuilder withParam(String key, String value) {
            Log.d("aaa", value);
            if (params == null)
                params = new HashMap<>();
            params.put(key, value);
            return this;
        }

        public UriBuilder withParam(String key, Object param) {
            return withParam(key, param.getClass().isPrimitive() || String.class.isInstance(param) ? String.valueOf(param) : ParcelJsonConverter.toJson(param));
        }

        public UriBuilder withParams(Map<String, String> params) {
            if (this.params == null)
                this.params = new HashMap<>();
            this.params.putAll(params);
            return this;
        }

        public Router build() {
            if (TextUtils.isEmpty(route))
                return null;

            Uri.Builder builder = Uri.parse(TextUtils.isEmpty(scheme) ? route : scheme + "://" + route).buildUpon();
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet)
                    builder.appendQueryParameter(key, params.get(key));
            }

            return Router.create(builder.build().toString());
        }
    }
}

package com.chrischeng.router.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.chrischeng.router.RouteManager;
import com.chrischeng.router.RouteTargetBundle;
import com.chrischeng.router.callback.RouteCallback;
import com.chrischeng.router.exception.RouterActivityNotFoundException;
import com.chrischeng.router.exception.RouterInterceptException;
import com.chrischeng.router.interceptor.RouterInterceptor;
import com.chrischeng.router.parser.RouteUriParser;
import com.chrischeng.router.rule.RouterRule;

import java.util.ArrayList;
import java.util.List;

public class RouteActivityLauncher {

    public static RouteActivityLauncher getInstance() {
        return Holder.instance;
    }

    public void launchActivity(Context context, Uri uri, RouteTargetBundle targetBundle) {
        if (context == null)
            context = RouteManager.getInstance().getContext();

        RouteCallback callback = targetBundle.callback;
        RouterRule rule = RouteManager.getInstance().findRule(RouteUriParser.parse(uri));

        if (!checkIntercept(context, uri, rule, targetBundle, callback))
            return;

        if (!checkRule(uri, rule, callback))
            return;

        Intent intent = createIntent(context, rule, targetBundle);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                activity.startActivityForResult(intent, targetBundle.requestCode, targetBundle.options);
            else
                activity.startActivityForResult(intent, targetBundle.requestCode);

            int enterAnim = targetBundle.enterAnim;
            if (enterAnim >= 0) {
                int exitAnim = targetBundle.exitAnim;
                if (exitAnim >= 0)
                    activity.overridePendingTransition(enterAnim, exitAnim);
            }
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        if (callback != null)
            callback.onSuccess(uri, rule);
    }

    private Intent createIntent(Context context, RouterRule rule, RouteTargetBundle targetBundle) {
        Intent intent = new Intent(context, rule.getTarget());
        if (targetBundle.extras != null)
            intent.putExtras(targetBundle.extras);
        intent.addFlags(targetBundle.flags);
        return intent;
    }

    private boolean checkIntercept(Context context, Uri uri, RouterRule rule, RouteTargetBundle targetBundle, RouteCallback callback) {
        try {
            iteratorAllInterceptors(context, uri, rule, targetBundle);
            targetBundle.removeAllInterceptors();
            return true;
        } catch (RouterInterceptException ex) {
            if (callback != null)
                callback.onFailed(uri, ex);
            return false;
        }
    }

    private void iteratorAllInterceptors(Context context, Uri uri, RouterRule rule, RouteTargetBundle targetBundle) {
        List<RouterInterceptor> interceptors = new ArrayList<>();

        List<Class<? extends RouterInterceptor>> pkgGlobalInterceptors = RouteManager.getInstance().getPackageGlobalInterceptors(uri.toString());
        if (pkgGlobalInterceptors != null && !pkgGlobalInterceptors.isEmpty()) {
            for (Class<? extends RouterInterceptor> interceptor : pkgGlobalInterceptors) {
                try {
                    interceptors.add(interceptor.newInstance());
                } catch (InstantiationException e) {
                    //
                } catch (IllegalAccessException e) {
                    //
                }
            }
        }

        Class<? extends RouterInterceptor>[] ruleInterceptors = rule.getInterceptors();
        if (ruleInterceptors != null && ruleInterceptors.length > 0)
            for (Class<? extends RouterInterceptor> interceptor : ruleInterceptors) {
                try {
                    interceptors.add(interceptor.newInstance());
                } catch (InstantiationException e) {
                    //
                } catch (IllegalAccessException e) {
                    //
                }
            }

        List<Class<? extends RouterInterceptor>> targetInterceptors = targetBundle.interceptors;
        if (targetInterceptors != null && !targetInterceptors.isEmpty()) {
            for (Class<? extends RouterInterceptor> interceptor : targetInterceptors) {
                try {
                    interceptors.add(interceptor.newInstance());
                } catch (InstantiationException e) {
                    //
                } catch (IllegalAccessException e) {
                    //
                }
            }
        }

        if (interceptors.isEmpty())
            return;

        for (RouterInterceptor interceptor : interceptors) {
            if (interceptor.intercept(context, uri)) {
                interceptor.onIntercepted(context, uri, targetBundle);
                throw new RouterInterceptException(interceptor.getClass().getSimpleName());
            }
        }
    }

    private boolean checkRule(Uri uri, RouterRule rule, RouteCallback callback) {
        if (rule != null)
            return true;

        if (callback != null)
            callback.onFailed(uri, new RouterActivityNotFoundException(uri.toString()));
        return false;
    }

    private RouteActivityLauncher() {
    }

    private static class Holder {
        private static RouteActivityLauncher instance = new RouteActivityLauncher();
    }
}

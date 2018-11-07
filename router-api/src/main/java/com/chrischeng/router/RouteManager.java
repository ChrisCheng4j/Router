package com.chrischeng.router;

import android.content.Context;

import com.chrischeng.router.creator.IRouterRuleCreator;
import com.chrischeng.router.interceptor.RouterInterceptor;
import com.chrischeng.router.parser.RouteUriInfo;
import com.chrischeng.router.rule.RouterRule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteManager {

    private static final String CLASS_RULE_CREATOR_SIMPLE_NAME = "RouterRuleCreator";

    private Context mContext;

    private String[] mPkgs;
    private Map<String, Set<String>> mPkgRoutes;
    private Map<String, RouterRule> mRouteRules;
    private Map<String, List<Class<? extends RouterInterceptor>>> mPkgInterceptors;

    public static RouteManager getInstance() {
        return Holder.instance;
    }

    void init(Context context) {
        mPkgs = new String[]{"com.chrischeng.router.demo"};
        mContext = context.getApplicationContext();
        collectInfo();
    }

    public Context getContext() {
        return mContext;
    }

    public RouterRule findRule(RouteUriInfo routeUriInfo) {
        String route = routeUriInfo.route;
        if (mRouteRules.containsKey(route))
            return mRouteRules.get(route);
        return null;
    }

    public List<Class<? extends RouterInterceptor>> getPackageGlobalInterceptors(String uri) {
        Iterator<Map.Entry<String, Set<String>>> iterator = mPkgRoutes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Set<String>> entry = iterator.next();
            if (entry.getValue().contains(uri))
                return mPkgInterceptors.get(entry.getKey());
        }

        return null;
    }

    private void collectInfo() {
        for (String pkg : mPkgs) {
            try {
                Class<?> cls = Class.forName(pkg + "." + CLASS_RULE_CREATOR_SIMPLE_NAME);
                IRouterRuleCreator creator = (IRouterRuleCreator) cls.newInstance();

                Map<String, RouterRule> routeRules = creator.createRules();
                List<Class<? extends RouterInterceptor>> pkgGlobalInterceptors = creator.createPackageGlobalInterceptors();

                if (pkgGlobalInterceptors != null && !pkgGlobalInterceptors.isEmpty())
                    mPkgRoutes.put(pkg, routeRules.keySet());
                mRouteRules.putAll(routeRules);
                mPkgInterceptors.put(pkg, pkgGlobalInterceptors);
            } catch (ClassNotFoundException e) {
                //
            } catch (IllegalAccessException e) {
                //
            } catch (InstantiationException e) {
                //
            }
        }
    }

    private RouteManager() {
        mPkgRoutes = new HashMap<>();
        mRouteRules = new HashMap<>();
        mPkgInterceptors = new HashMap<>();
    }

    private static class Holder {
        private static RouteManager instance = new RouteManager();
    }
}

package com.chrischeng.router.rule;

import com.chrischeng.router.interceptor.RouterInterceptor;

public class RouterRule {

    private Class mTarget;
    private Class<? extends RouterInterceptor>[] mInterceptors;

    public RouterRule(Class target) {
        mTarget = target;
    }

    public Class getTarget() {
        return mTarget;
    }

    public Class<? extends RouterInterceptor>[] getInterceptors() {
        return mInterceptors;
    }

    public RouterRule addInterceptors(Class<? extends RouterInterceptor>... classes) {
        mInterceptors = classes;
        return this;
    }
}

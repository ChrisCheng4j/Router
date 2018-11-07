package com.chrischeng.router.creator;

import com.chrischeng.router.interceptor.RouterInterceptor;
import com.chrischeng.router.rule.RouterRule;

import java.util.List;
import java.util.Map;

public interface IRouterRuleCreator {

    Map<String, RouterRule> createRules();

    List<Class<? extends RouterInterceptor>> createPackageGlobalInterceptors();
}

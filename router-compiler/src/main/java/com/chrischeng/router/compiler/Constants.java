package com.chrischeng.router.compiler;

public class Constants {

    private static final String PACKAGE_NAME = "com.chrischeng.router";

    static final String INTERFACE_CREATOR_RULE_PACKAGE_NAME = PACKAGE_NAME + ".creator";
    static final String INTERFACE_CREATOR_RULE_SIMPLE_NAME = "IRouterRuleCreator";
    static final String INTERFACE_CREATOR_RULE_FULL_NAME = INTERFACE_CREATOR_RULE_PACKAGE_NAME + "." + INTERFACE_CREATOR_RULE_SIMPLE_NAME;

    static final String CLASS_RULE_PACKAGE_NAME = PACKAGE_NAME + ".rule";
    static final String CLASS_RULE_SIMPLE_NAME = "RouterRule";

    static final String CLASS_INTERCEPTOR_PACKAGE_NAME = PACKAGE_NAME + ".interceptor";
    static final String CLASS_INTERCEPTOR_SIMPLE_NAME = "RouterInterceptor";

    static final String GENERATE_CLASS_SIMPLE_NAME = "RouterRuleCreator";
    static final String GENERATE_METHOD_CREATE_RULES_NAME = "createRules";
    static final String GENERATE_METHOD_CREATE_INTERCEPTORS_NAME = "createPackageGlobalInterceptors";
    static final String GENERATE_BLOCK_INTERCEPTORS = "addInterceptors";
}

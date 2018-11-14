package com.chrischeng.router.compiler;

public class Constants {

    private static final String PACKAGE_NAME = "com.chrischeng.router";

    public static final String INTERFACE_CREATOR_RULE_PACKAGE_NAME = PACKAGE_NAME + ".creator";
    public static final String INTERFACE_CREATOR_RULE_SIMPLE_NAME = "IRouterRuleCreator";
    public static final String INTERFACE_CREATOR_RULE_FULL_NAME = INTERFACE_CREATOR_RULE_PACKAGE_NAME + "." + INTERFACE_CREATOR_RULE_SIMPLE_NAME;

    public static final String CLASS_RULE_PACKAGE_NAME = PACKAGE_NAME + ".rule";
    public static final String CLASS_RULE_SIMPLE_NAME = "RouterRule";

    public static final String CLASS_INTERCEPTOR_PACKAGE_NAME = PACKAGE_NAME + ".interceptor";
    public static final String CLASS_INTERCEPTOR_SIMPLE_NAME = "RouterInterceptor";

    public static final String GENERATE_CLASS_SIMPLE_NAME = "RouterRuleCreator";
    public static final String GENERATE_METHOD_CREATE_RULES_NAME = "createRules";
    public static final String GENERATE_METHOD_CREATE_INTERCEPTORS_NAME = "createPackageGlobalInterceptors";
    public static final String GENERATE_BLOCK_INTERCEPTORS = "addInterceptors";
}

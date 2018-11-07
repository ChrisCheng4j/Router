package com.chrischeng.router.annotation;

import com.chrischeng.router.AnnotationDefaultInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterConfig {

    String baseScheme() default "router";

    String pkg() default "com.chrischeng.router";

    RouterInterceptor globalInterceptors() default @RouterInterceptor(AnnotationDefaultInterceptor.class);
}

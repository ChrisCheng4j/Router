package com.chrischeng.router.compiler.model;

import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.annotation.RouterRule;
import com.chrischeng.router.compiler.exception.RouterAnnotationValueException;
import com.squareup.javapoet.ClassName;

import java.net.URI;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

public class RouteRuleConfig {

    public TypeElement element;
    public String[] routes;
    public ClassName[] interceptors;

    public static RouteRuleConfig create(RouteBasicConfig routeBasicConfig, RouterRule routerRule, TypeElement element) {
        RouteRuleConfig ruleConfig = new RouteRuleConfig();
        ruleConfig.element = element;
        ruleConfig.routes = ruleConfig.combineRoutes(routeBasicConfig, routerRule, element);
        ruleConfig.interceptors = ruleConfig.combineInterceptors(element);
        return ruleConfig;
    }

    private String[] combineRoutes(RouteBasicConfig routeBasicConfig, RouterRule routerRule, TypeElement element) {
        String[] routerRoutes = routerRule.value();

        if (routerRoutes.length == 0)
            return new String[]{};

        String baseScheme = (routeBasicConfig != null) ? routeBasicConfig.baseScheme : "";
        URI uri;
        String scheme;
        String route;

        for (int i = 0; i < routerRoutes.length; i++) {
            route = routerRoutes[i];
            uri = URI.create(routerRoutes[i]);
            scheme = uri.getScheme();
            if (scheme == null || scheme.length() == 0) {
                if (baseScheme.length() == 0)
                    throw new RouterAnnotationValueException(String.format("Could not found scheme with %1s annotated by %2s", element.getSimpleName(), RouterRule.class.getSimpleName()));
                route = baseScheme + "://" + route;
                routerRoutes[i] = route;
            }
        }

        return routerRoutes;
    }

    private ClassName[] combineInterceptors(TypeElement type) {
        ClassName[] interceptors;

        RouterInterceptor interceptor = type.getAnnotation(RouterInterceptor.class);

        if (interceptor == null)
            return null;

        try {
            Class[] classes = interceptor.value();

            int length = classes.length;
            if (length == 0)
                return null;

            interceptors = new ClassName[length];
            for (int i = 0; i < length; i++)
                interceptors[i] = ClassName.get(classes[i].getPackage().getName(), classes[i].getSimpleName());

        } catch (MirroredTypesException mirrored) {
            List<? extends TypeMirror> typeMirrors = mirrored.getTypeMirrors();

            if (typeMirrors == null)
                return null;

            int size = typeMirrors.size();
            if (size == 0)
                return null;

            interceptors = new ClassName[size];
            for (int i = 0; i < size; i++)
                interceptors[i] = (ClassName) ClassName.get(typeMirrors.get(i));
        }

        return interceptors;
    }
}

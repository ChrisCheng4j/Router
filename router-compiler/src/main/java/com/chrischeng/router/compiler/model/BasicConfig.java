package com.chrischeng.router.compiler.model;

import com.chrischeng.router.AnnotationDefaultInterceptor;
import com.chrischeng.router.annotation.RouterConfig;
import com.squareup.javapoet.ClassName;

import java.util.List;

import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

public class BasicConfig {

    public String baseScheme;
    public String pkg;
    public ClassName[] interceptors;

    public BasicConfig(RouterConfig config) {
        baseScheme = config.baseScheme();
        pkg = config.pkg();
        parseInterceptors(config);
    }

    private void parseInterceptors(RouterConfig config) {
        interceptors = new ClassName[0];

        try {
            Class[] classes = config.globalInterceptors().value();
            int length = classes.length;
            if (length == 1 && AnnotationDefaultInterceptor.class.isAssignableFrom(classes[0]))
                return;

            interceptors = new ClassName[length];
            for (int i = 0; i < length; i++)
                interceptors[i] = ClassName.get(classes[i].getPackage().getName(), classes[i].getSimpleName());

        } catch (MirroredTypesException mirrored) {
            List<? extends TypeMirror> typeMirrors = mirrored.getTypeMirrors();
            if (typeMirrors == null)
                return;

            int length = typeMirrors.size();
            if (length == 1 && ((ClassName) ClassName.get(typeMirrors.get(0))).reflectionName().equals(AnnotationDefaultInterceptor.class.getCanonicalName()))
                return;

            interceptors = new ClassName[length];
            for (int i = 0; i < length; i++)
                interceptors[i] = (ClassName) ClassName.get(typeMirrors.get(i));
        }
    }
}

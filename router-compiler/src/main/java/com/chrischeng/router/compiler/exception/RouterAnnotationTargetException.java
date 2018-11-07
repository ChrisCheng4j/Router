package com.chrischeng.router.compiler.exception;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;

public class RouterAnnotationTargetException extends RuntimeException {

    public RouterAnnotationTargetException(Name simpleName, Class annotation, ElementKind kind) {
        super(String.format("The element with %1s you are annotated by %2s must be a %3s", simpleName.toString(), annotation.getSimpleName(), kind.name()));
    }
}

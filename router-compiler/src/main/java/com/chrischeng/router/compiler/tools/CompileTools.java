package com.chrischeng.router.compiler.tools;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class CompileTools {

    private static CompileTools tool = new CompileTools();

    private Elements elements;
    private Filer filer;
    private Types types;
    private Messager messager;

    public static CompileTools get() {
        return tool;
    }

    public void init(ProcessingEnvironment processingEnv) {
        elements = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        types = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
    }

    public Elements getElementUtils() {
        return elements;
    }

    public Filer getFiler() {
        return filer;
    }

    public Types getTypeUtil() {
        return types;
    }

    public void printMessage(String msg) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }
}

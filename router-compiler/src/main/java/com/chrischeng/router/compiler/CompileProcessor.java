package com.chrischeng.router.compiler;

import com.chrischeng.router.annotation.RouterAction;
import com.chrischeng.router.annotation.RouterConfig;
import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.compiler.exception.RouterAnnotationTargetException;
import com.chrischeng.router.compiler.generator.RouteRuleGenerator;
import com.chrischeng.router.compiler.model.RouteBasicConfig;
import com.chrischeng.router.compiler.model.RouteRuleConfig;
import com.chrischeng.router.compiler.tools.CompileTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class CompileProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        CompileTools.get().init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        processRules(roundEnv, processConfig(roundEnv));
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();

        set.add(RouterAction.class.getCanonicalName());
        set.add(RouterConfig.class.getCanonicalName());
        set.add(RouterInterceptor.class.getCanonicalName());

        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private RouteBasicConfig processConfig(RoundEnvironment roundEnv) {
        RouteBasicConfig config = null;

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterConfig.class);
        if (elements == null || elements.isEmpty())
            return null;

        for (Element element : elements) {
            if (element.getKind() != ElementKind.CLASS)
                throw new RouterAnnotationTargetException(element.getSimpleName(), RouterConfig.class, ElementKind.CLASS);

            config = new RouteBasicConfig(element.getAnnotation(RouterConfig.class));
            break;
        }

        return config;
    }

    private void processRules(RoundEnvironment roundEnv, RouteBasicConfig routeBasicConfig) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterAction.class);

        if (elements == null || elements.isEmpty())
            return;

        List<RouteRuleConfig> ruleConfigs = new ArrayList<>();
        for (Element element : elements) {
            if (element.getKind() != ElementKind.CLASS)
                throw new RouterAnnotationTargetException(element.getSimpleName(), RouterAction.class, ElementKind.CLASS);

            ruleConfigs.add(RouteRuleConfig.create(routeBasicConfig, element.getAnnotation(RouterAction.class), (TypeElement) element));
        }

        try {
            RouteRuleGenerator.generateCode(routeBasicConfig, ruleConfigs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.chrischeng.router.compiler;

import com.chrischeng.router.compiler.exception.RouterInternalException;
import com.chrischeng.router.compiler.model.BasicConfig;
import com.chrischeng.router.compiler.model.RouterRuleConfig;
import com.chrischeng.router.compiler.tools.CompileTools;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

class RouterRuleFactory {

    static void generateCode(BasicConfig basicConfig, List<RouterRuleConfig> rules) throws IOException {
        String creatorName = Constants.INTERFACE_CREATOR_RULE_FULL_NAME;
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(Constants.GENERATE_CLASS_SIMPLE_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get(Constants.INTERFACE_CREATOR_RULE_PACKAGE_NAME, Constants.INTERFACE_CREATOR_RULE_SIMPLE_NAME));
        typeBuilder.addMethod(generateRulesMethod(creatorName, rules));
        typeBuilder.addMethod(generateInterceptorsMethod(creatorName, basicConfig.interceptors));

        JavaFile.builder(basicConfig.pkg, typeBuilder.build()).build().writeTo(CompileTools.get().getFiler());
    }

    private static MethodSpec generateRulesMethod(String creatorName, List<RouterRuleConfig> rules) {
        MethodSpec.Builder methodBuilder = MethodSpec.overriding(getOverrideMethod(CompileTools.get().getElementUtils(), creatorName, Constants.GENERATE_METHOD_CREATE_RULES_NAME))
                .addModifiers(Modifier.PUBLIC);

        ClassName ruleClassName = ClassName.get(Constants.CLASS_RULE_PACKAGE_NAME, Constants.CLASS_RULE_SIMPLE_NAME);
        methodBuilder.addStatement("$T<String, $T> rules = new $T<>()", Map.class, ruleClassName, HashMap.class);

        if (rules != null && rules.size() > 0) {
            for (RouterRuleConfig rule : rules) {
                String[] routes = rule.routes;
                for (String route : routes)
                    appendRulesMethod(methodBuilder, rule, route, ruleClassName);
            }
        }

        methodBuilder.addStatement("return rules");

        return methodBuilder.build();
    }

    private static MethodSpec generateInterceptorsMethod(String creatorName, ClassName[] interceptors) {
        MethodSpec.Builder methodBuilder = MethodSpec.overriding(getOverrideMethod(CompileTools.get().getElementUtils(), creatorName, Constants.GENERATE_METHOD_CREATE_INTERCEPTORS_NAME))
                .addModifiers(Modifier.PUBLIC);

        ClassName interceptorClassName = ClassName.get(Constants.CLASS_INTERCEPTOR_PACKAGE_NAME, Constants.CLASS_INTERCEPTOR_SIMPLE_NAME);
        methodBuilder.addStatement("$T<$T<? extends $T>> interceptors = new $T<>()", List.class, Class.class, interceptorClassName, ArrayList.class);

        if (interceptors != null && interceptors.length > 0) {
            for (ClassName interceptor : interceptors)
                appendInterceptorsMethod(methodBuilder, interceptor);
        }

        methodBuilder.addStatement("return interceptors");

        return methodBuilder.build();
    }

    private static ExecutableElement getOverrideMethod(Elements elements, String creatorName, String methodName) {
        TypeElement typeElement = elements.getTypeElement(creatorName);
        if (typeElement != null) {
            List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
            if (enclosedElements != null) {
                for (Element element : enclosedElements) {
                    if (element.getKind() != ElementKind.METHOD || !methodName.equals(element.getSimpleName().toString()))
                        continue;
                    return (ExecutableElement) element;
                }
            }
        }

        throw new RouterInternalException(String.format("Could not found method %s when getOverrideMethod during generate code.", methodName));
    }

    private static void appendRulesMethod(MethodSpec.Builder methodBuilder, RouterRuleConfig routerRuleConfig, String route, ClassName ruleClassName) {
        CodeBlock.Builder blockBuilder = CodeBlock.builder().add("rules.put($S, new $T($T.class)",
                route, ruleClassName, routerRuleConfig.element);

        ClassName[] interceptors = routerRuleConfig.interceptors;
        if (interceptors != null && interceptors.length > 0) {
            blockBuilder.add("\r\n\t\t");
            blockBuilder.add(String.format(".%s(", Constants.GENERATE_BLOCK_INTERCEPTORS));

            for (int i = 0; i < interceptors.length; i++) {
                if (i > 0)
                    blockBuilder.add(", ");
                blockBuilder.add("$T.class", interceptors[i]);
            }
            blockBuilder.add(")");
        }

        blockBuilder.addStatement(")");
        methodBuilder.addCode(blockBuilder.build());
    }

    private static void appendInterceptorsMethod(MethodSpec.Builder methodBuilder, ClassName interceptorClassName) {
        CodeBlock.Builder blockBuilder = CodeBlock.builder().add("interceptors.add($T.class)", interceptorClassName);
        blockBuilder.addStatement("");
        methodBuilder.addCode(blockBuilder.build());
    }
}

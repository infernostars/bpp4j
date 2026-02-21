package dev.infernity.whirling.bpp4j.lang.interpreter;

import dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppExtension;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppFunction;
import dev.infernity.whirling.bpp4j.lang.parsing.Node;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppExtension.defaultExtensions;

public class InterpreterState {
    String source;
    List<Node> nodes;
    Environment rootEnvironment;
    ArrayList<BppExtension> extensions;

    private HashMap<String, Method> methodMap;

    private static HashMap<String, Method> getBppFunctionsForExtension(Class<? extends BppExtension> extClass) {
        HashMap<String, Method> annotatedMethods = new HashMap<>();
        for (Method method : extClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BppFunction.class)) {
                if (!Modifier.isPublic(method.getModifiers())) {
                    throw new IllegalStateException("In extension %s, method %s is annotated as a BppFunction but is not public!".formatted(extClass.getName(), method.getName()));
                }
                BppFunction funcAnnotation = method.getAnnotation(BppFunction.class);
                annotatedMethods.put(funcAnnotation.value(), method);
            }
        }
        return annotatedMethods;
    }

    public InterpreterState(String source, List<Node> nodes) {
        this.source = source;
        this.nodes = nodes;
        this.rootEnvironment = new Environment();
        this.methodMap = new HashMap<>();
        extensions = new ArrayList<>();
        initializeExtensions(defaultExtensions());
    }

    private void initializeExtensions(List<BppExtension> bppExtensions){
        for (BppExtension extension : bppExtensions){
            var ext = extension.withRootEnvironment(rootEnvironment);
            var functionMethodMap = getBppFunctionsForExtension(extension.getClass());
            functionMethodMap.forEach((key, value) -> {
                methodMap.merge(key, value, (_, _) -> {
                    throw new IllegalStateException("Extension %s is trying to add function %s but function is already declared by an earlier extension!".formatted(extension.getClass().getName(), key));
                });
            });
            extensions.add(ext);
        }
    }
}

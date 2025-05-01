package com.empresa.testgen;

import com.empresa.testgen.elements.Dependency;
import com.empresa.testgen.elements.MethodInfo;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceAnalyzer {
    public static class Result {
        public String packageName;
        public String className;
        public String testClassName;
        public String instanceName;
        public List<Dependency> dependencies;
        public List<MethodInfo> testMethods;
    }

    public static Result analyze(File file) {
        Result result = new Result();
        result.dependencies = new ArrayList<>();
        result.testMethods = new ArrayList<>();

        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            cu.getPackageDeclaration().ifPresent(p -> result.packageName = p.getNameAsString());

            ClassOrInterfaceDeclaration clazz = cu.getClassByName(file.getName().replace(".java", "")).get();
            result.className = clazz.getNameAsString();
            result.testClassName = clazz.getNameAsString() + "Test";
            result.instanceName = decapitalize(clazz.getNameAsString());


            // Procesar campos con @Autowired y campos finales
            for (FieldDeclaration field : clazz.getFields()) {
                boolean isAutowired = field.isAnnotationPresent("Autowired");
                boolean isFinal = field.getModifiers().contains(Modifier.finalModifier());
                boolean isStatic = field.getModifiers().contains(Modifier.staticModifier());

                if (isAutowired || (isFinal && !isStatic)) {
                    field.getVariables().forEach(var -> {
                        Dependency dep = new Dependency();
                        dep.type = field.getElementType().asString();
                        dep.name = var.getNameAsString();
                        dep.instanceName = var.getNameAsString();

                        try {
                            ResolvedReferenceType resolvedType = (ResolvedReferenceType) field.getElementType().resolve();
                            if (resolvedType.isReferenceType()) {
                                Optional<ResolvedReferenceTypeDeclaration> typeDeclaration = resolvedType.getTypeDeclaration();
                                if (typeDeclaration.isPresent()) {
                                    if (typeDeclaration.get().getPackageName() != null && !typeDeclaration.get().getPackageName().isEmpty()
                                            && !typeDeclaration.get().getPackageName().equals(result.packageName)) {
                                        dep.packageName = typeDeclaration.get().getPackageName();
                                    }
                                }

                            }
                            System.out.println("Paquete de la dependencia " + dep.name + ": " + dep.packageName);
                        } catch (Exception e) {
                            dep.packageName = null;
                            System.out.println("No se pudo resolver el paquete de la dependencia " + dep.name + ": " + e.getMessage());
                        }
                        if (!result.dependencies.stream().anyMatch(d -> d.name.equals(dep.name))) {
                            result.dependencies.add(dep);
                        }
                    });
                }
            }

            clazz.getMethods().stream()
                    .filter(m -> m.isPublic())
                    .forEach(method -> {
                        MethodInfo mi = new MethodInfo();
                        mi.name = method.getNameAsString().trim();
                        mi.params = method.getParameters().stream()
                                .map(p -> p.getNameAsString())
                                .collect(Collectors.joining(", "));
                        mi.mocks = new ArrayList<>();
                        mi.mocks.add("..."); // simple stub
                        result.testMethods.add(mi);
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String decapitalize(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
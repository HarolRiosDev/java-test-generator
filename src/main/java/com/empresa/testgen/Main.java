package com.empresa.testgen;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: generate-tests <ruta-clase-o-paquete>");
            return;
        }

        File input = new File(args[0]);
        List<File> javaFiles = FileScanner.findJavaFiles(input);

        for (File file : javaFiles) {
            ServiceAnalyzer.Result result = ServiceAnalyzer.analyze(file);
            TestGenerator.generateTest(result);
        }
    }
}

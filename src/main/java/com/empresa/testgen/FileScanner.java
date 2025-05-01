package com.empresa.testgen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {
    public static List<File> findJavaFiles(File input) {
        List<File> result = new ArrayList<>();
        if (input.isFile() && input.getName().endsWith(".java")) {
            result.add(input);
        } else if (input.isDirectory()) {
            File[] files = input.listFiles();
            if (files != null) {
                for (File f : files) {
                    result.addAll(findJavaFiles(f));
                }
            }
        }
        return result;
    }
}

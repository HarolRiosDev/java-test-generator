package com.empresa.testgen;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGenerator {
    public static void generateTest(ServiceAnalyzer.Result data) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setClassLoaderForTemplateLoading(TestGenerator.class.getClassLoader(), "/templates");
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate("test-class.ftl", "UTF-8");

            Map<String, Object> model = new HashMap<>();
            model.put("packageName", data.packageName);
            model.put("className", data.className);
            model.put("testClassName", data.testClassName);
            model.put("instanceName", data.instanceName);
            model.put("dependencies", data.dependencies);
            model.put("testMethods", data.testMethods);
            System.out.println("Paquete de la clase de servicio: " + data.packageName); // Añade esta línea

            //Por si se quiere añadir imports especificos
            //model.put("imports", List.of("static org.mockito.Mockito.*"));

            File outDir = new File("src/test/java/" + data.packageName.replace('.', '/'));
            outDir.mkdirs();
            File outFile = new File(outDir, data.testClassName + ".java");

            try (Writer writer = new FileWriter(outFile)) {
                template.process(model, writer);
            }

            System.out.println("Test generado: " + outFile.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

# Generación Automática de Tests con testgen

Este proyecto (`testgen`) permite generar automáticamente clases de test JUnit para servicios Java. A continuación, se describen los pasos para utilizarlo en otro proyecto Java.

## Prerrequisitos

* **Java Development Kit (JDK):** Asegúrate de tener instalado un JDK compatible con tu proyecto y con `testgen`.
* **Gradle:** El proyecto `testgen` utiliza Gradle como sistema de construcción. Asegúrate de tener Gradle instalado en tu sistema o que el proyecto destino también utilice Gradle (para facilitar la ejecución).

## Pasos para Generar Tests

1.  **Clonar el proyecto `testgen`:**

    Primero, necesitas tener el código fuente del proyecto `testgen` disponible localmente. Clónalo desde tu repositorio (e.g., GitHub, GitLab, Bitbucket) utilizando Git:

    ```bash
    git clone <URL_DEL_REPOSITORIO_TESTGEN>
    cd testgen
    ```

2.  **Construir el proyecto `testgen`:**

    Dentro del directorio raíz del proyecto `testgen`, utiliza Gradle para construir el proyecto y crear un ejecutable (si es necesario, aunque en este caso, lo ejecutaremos directamente con Gradle).

    ```bash
    ./gradlew build
    ```

    o en Windows:

    ```bash
    gradlew.bat build
    ```

    Esto compilara el código y descargará las dependencias necesarias (FreeMarker, JavaParser, JUnit, Mockito).

3.  **Identificar la clase o el paquete del proyecto destino para generar los tests:**

    Necesitas saber la ruta al archivo `.java` de la clase de servicio para la que quieres generar el test, o la ruta al paquete que contiene múltiples servicios.

4.  **Ejecutar `testgen` para generar los tests:**

    Utiliza el comando `gradlew run` dentro del directorio raíz del proyecto `testgen`, pasando como argumento la ruta a la clase o al paquete del proyecto destino.

    * **Para una clase específica:**

        ```bash
        ./gradlew run --args="<RUTA_COMPLETA_AL_ARCHIVO_JAVA_DEL_SERVICIO>"
        ```

      Ejemplo (ajusta la ruta según tu proyecto destino):

        ```bash
        ./gradlew run --args="/ruta/a/tu/otro/proyecto/src/main/java/com/ejemplo/servicio/MiServicio.java"
        ```

      o en Windows:

        ```bash
        gradlew.bat run --args="C:\ruta\a\tu\otro\proyecto\src\main\java\com\ejemplo\servicio\MiServicio.java"
        ```

    * **Para un paquete completo:**

        ```bash
        ./gradlew run --args="<RUTA_COMPLETA_AL_DIRECTORIO_DEL_PAQUETE>"
        ```

      Ejemplo (ajusta la ruta según tu proyecto destino):

        ```bash
        ./gradlew run --args="/ruta/a/tu/otro/proyecto/src/main/java/com/ejemplo/servicio"
        ```

      o en Windows:

        ```bash
        gradlew.bat run --args="C:\ruta\a\tu\otro\proyecto\src\main\java\com\ejemplo\servicio"
        ```

    **Importante:** Asegúrate de usar la ruta completa al archivo `.java` o al directorio del paquete dentro del proyecto destino.

5.  **Verificar los tests generados:**

    Los archivos de test generados se crearán dentro del proyecto `testgen`, en la estructura de directorios `src/test/java/` replicando la estructura de paquetes de la clase de servicio analizada. Por ejemplo, si analizaste `com.ejemplo.servicio.MiServicio.java`, el test `MiServicioTest.java` se encontrará en `testgen/src/test/java/com/ejemplo/servicio/`.

6.  **Mover los tests al proyecto destino (opcional):**

    Si deseas que los tests formen parte del proyecto destino, deberás mover los archivos generados desde la ubicación en `testgen` a la ubicación correspondiente dentro de la estructura de directorios de test de tu proyecto destino (generalmente `src/test/java/`).

## Consideraciones Adicionales

* **Rutas relativas:** Si ejecutas el comando `gradlew run` desde un directorio que tiene una relación conocida con la ruta del proyecto destino, podrías intentar usar rutas relativas en el argumento `--args`.
* **Dependencias del proyecto destino:** Asegúrate de que el proyecto `testgen` tenga las dependencias necesarias (como JavaParser y FreeMarker) para analizar correctamente las clases del proyecto destino. Gradle se encarga de esto dentro del propio `testgen`.
* **Personalización de la plantilla:** Puedes modificar el archivo `src/main/resources/templates/test-class.ftl` dentro del proyecto `testgen` para personalizar la forma en que se generan los tests (por ejemplo, cambiar las aserciones predeterminadas, añadir más anotaciones, etc.).

¡Espero que esta guía te sea de gran utilidad para generar tests en tus otros proyectos! Si tienes alguna pregunta o necesitas ayuda con algún paso específico, no dudes en consultarme.
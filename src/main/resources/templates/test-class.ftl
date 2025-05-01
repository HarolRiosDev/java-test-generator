package ${packageName};

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

<#-- Importar los paquetes de las dependencias -->
<#list dependencies as dep>
  <#if dep.packageName?? && dep.packageName != packageName>
import ${dep.packageName}.${dep.type};
  </#if>
</#list>

class ${testClassName} {

<#list dependencies as dep>
    @Mock
    private ${dep.type} ${dep.name};
</#list>

    @InjectMocks
    private ${className} ${instanceName};

    public ${testClassName}() {
        MockitoAnnotations.openMocks(this);
    }

<#list testMethods as method>
   <#if method.name??>
    /**
     * Method to test ${className}.${method.name}
     */
    @Test
    void ${method.name}Test() {
      // Arrange
      <#list method.mocks as mock>
      // when(${mock}).thenReturn(...);
      </#list>

      // Act
      // ${instanceName}.${method.name}(${method.params});

      // Assert
      // assertEquals(...);
    }
  <#else>
    <#-- Log en el archivo generado si el nombre falta -->
    // ¡¡¡ERROR: Metodo sin nombre!!! Objeto: ${method?string}
  </#if>

</#list>
}

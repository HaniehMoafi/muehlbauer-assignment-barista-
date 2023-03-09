package de.muehlbauer.assignment.barista.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Barista Service API", version = "1.0", description = "api document for barista service"))
public class SwaggerConfiguration {
}

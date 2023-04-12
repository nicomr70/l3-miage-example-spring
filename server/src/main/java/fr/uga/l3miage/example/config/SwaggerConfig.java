package fr.uga.l3miage.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cette classe correspond à une configuration pour le swagger de l'application<br>
 * Les Annotations :
 * <ul>
 *     <li>{@link Configuration} permet de créer une configuration spring, cette annotation est en quelque sorte un stéréotype d'un @{@link Bean}, car elle va permettre de créer un candidat à l'injection.</li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Example Api for L3 MIAGE")
                        .version("1.0.0")
                        .description("Example Api for L3 MIAGE - API Swagger documentation"));
    }
}

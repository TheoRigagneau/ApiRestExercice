package com.formation.api_rest_demo.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST Produits")
                        .version("1.0.0")
                        .description("API de gestion du catalogue produits — Formation Spring Boot")
                                        .contact(new Contact()
                                                .name("Équipe Formation")
                                                .email("formation@exemple.fr")));
    }
}
package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.Scopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme oauthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
                                .tokenUrl("https://oauth2.googleapis.com/token")
                                .scopes(new Scopes()
                                        .addString("profile", "Información del perfil")
                                        .addString("email", "Dirección de correo")
                                )
                        )
                );

        return new OpenAPI()
                .info(new Info()
                        .title("PetFinder API")
                        .version("1.0")
                        .description("API para gestión de mascotas y avistamientos.")
                        .contact(new Contact()
                                .name("Tu Nombre")
                                .email("tuemail@dominio.com")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("oauth2"))
                .schemaRequirement("oauth2", oauthScheme);
    }
}

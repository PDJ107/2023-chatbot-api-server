package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "학사 도우미 챗봇 API 명세서",
                description = "학사 정보 및 편의기능을 제공하는 챗봇 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
//    @Bean
//    public GroupedOpenApi chatbotOpenApi() {
//        String[] paths = {"/v1/**"};
//
//        return GroupedOpenApi.builder()
//                .group("챗봇 API v1")
//                .pathsToMatch(paths)
//                .build();
//    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Components section defines Security Scheme "mySecretHeader"
                .components(new Components()
                        .addSecuritySchemes("authorizationHeader", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                // AddSecurityItem section applies created scheme globally
                .addSecurityItem(new SecurityRequirement().addList("authorizationHeader"));
    }
}
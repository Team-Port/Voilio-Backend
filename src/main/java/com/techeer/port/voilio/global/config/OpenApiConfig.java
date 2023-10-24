package com.techeer.port.voilio.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/** Swagger springdoc-ui 구성 파일 */
@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI openAPI() {
    Info info =
        new Info()
            .title("Voilio API Document")
            .version("v0.0.1")
            .description("Voilio 프로젝트의 API 명세서입니다.");

    String jwtSchemeName = "jwtAuth";

    // API 요청헤더에 인증정보 포함
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

    // SecuritySchemes 등록
    Components components =
        new Components()
            .addSecuritySchemes(
                jwtSchemeName,
                new SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT"));

    SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthenticationPrincipal.class);

    return new OpenAPI().info(info).addSecurityItem(securityRequirement).components(components);
  }
}

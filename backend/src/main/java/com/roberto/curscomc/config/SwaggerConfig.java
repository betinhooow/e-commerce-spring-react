package com.roberto.curscomc.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
	            .useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.roberto.curscomc"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Aplication API")
				.description("API REST Documentation")
				.version("1.0")
				.build();
	}
	
	private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
      }

    private List<SecurityReference> defaultAuth() {
      final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
      final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
      return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
    }

    private ApiKey apiKey() {
      return new ApiKey("Bearer", "Authorization", "header");
    } 
}
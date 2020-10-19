package com.softplan.desafio.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.models.auth.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Value("${project.name}")
	private String title;
	
	@Value("${project.description}")
	private String description = null;
	
	@Value("${project.version}")
	private String version;

	private String termsOfServiceUrl = null;
	private Contact contact = null;
	private String license = null;
	private String licenseUrl = null;
    
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
				.securitySchemes(newSchemeArray(apiKey()))
				.securityContexts(Arrays.asList(securityContext()))
                .select()
                	.apis(RequestHandlerSelectors.basePackage("com.softplan.desafio.api.controller"))
                	.paths(PathSelectors.any())
                	.build()
                .apiInfo(new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, Collections.emptyList()));
	}
	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.any())
			.build();
	}
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("ADMIN", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(
	        new SecurityReference("Token Access", authorizationScopes));
	}
	private ApiKey apiKey() {
	    return new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name());
	}
	private List<? extends SecurityScheme> newSchemeArray(SecurityScheme o){
		List<SecurityScheme> array = new ArrayList<>();
		array.add(o);
		return array;
	}
}

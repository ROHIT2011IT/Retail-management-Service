package com.auction.retailService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.auction.retailService.controller")).paths(regex("/.*"))
				.build().apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("Spring Boot REST API").description("Retail Service REST API")
				.contact(new Contact("Retail manager", "www.retail.com", "reatilService@gmail.com")).license("License")
				.licenseUrl("https://retail.com/licensing").version("1.0.0").build();
		return apiInfo;
	}
}

package com.devkbil.mtssbj.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import springfox.bean.validators.plugins.Validators;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class SpringFoxConfig {

    @Value("${info.contact.mail}")
    String myEmail;
    @Value("${info.contact.phone}")
    String myPhone;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
        //.securitySchemes(Arrays.asList(securityScheme()))
        //.securityContexts(Arrays.asList(securityContext()))
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My REST API")
                .description("Some custom description of API")
                .version("0.1")
                .contact(new Contact("admin", "~", myEmail))
                .build();
        /*
        return new ApiInfo(
                "My REST API",
                "Some custom description of API.",
                "API TOS",
                "Terms of service",
                new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
         */
    }
}

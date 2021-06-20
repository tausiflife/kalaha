package com.games.kalah.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo = new ApiInfo(
            "Kalah for Backbase interview",
            "A simple 6-stone Kalah game",
            "1.0.0",
            "",
            new Contact("Tausif Shakeel", "https://www.linkedin.com/in/sk-tausif-shakeel-28656a30/", "tausif.056@gmail.com"),
            "",
            "",
            Collections.emptyList()
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.games.kalah.ui.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}

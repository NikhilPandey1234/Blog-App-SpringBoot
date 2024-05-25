package com.bloggApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwagerConfig {

    @Bean
    public Docket api (){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }


    private ApiInfo getInfo() {
        return new ApiInfo("Backend Blog App", "This Project Developed By Nikhil","1.0","Terms of Service", new Contact("Nikhil", "Www.BlogApp.com", "nikhil@gmail.com"), "Licence of Api", "Api Licence URL", Collections.emptyList() );
    }

}

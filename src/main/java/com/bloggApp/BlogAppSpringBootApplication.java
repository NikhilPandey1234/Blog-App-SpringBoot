package com.bloggApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppSpringBootApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){return new ModelMapper();
	}



}

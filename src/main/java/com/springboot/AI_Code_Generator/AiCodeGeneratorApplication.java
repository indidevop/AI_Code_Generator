package com.springboot.AI_Code_Generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:.env")
public class AiCodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiCodeGeneratorApplication.class, args);
	}

}

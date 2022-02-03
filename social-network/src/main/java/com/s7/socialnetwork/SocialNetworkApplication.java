package com.s7.socialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Social network API", version = "1.0", description = "Made for S7 Travel Retail testTask", termsOfService = "https://www.apache.org/licenses/LICENSE-2.0", contact = @Contact(name = "Sofia Kim", email = "sophianpilova@gmail.com"), license = @License(name = "Apache license Version 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
@SpringBootApplication
public class SocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
		System.out.println("run");
	}

}

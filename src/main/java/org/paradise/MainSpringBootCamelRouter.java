package org.paradise;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainSpringBootCamelRouter extends FatJarRouter {

	public static void main(String[] args) {
		SpringApplication.run(MainSpringBootCamelRouter.class, args);
	}

	@Override
	public void configure() {

		from("timer:trigger")
				.transform().simple("ref:myBean")
				.to("log:out");
	}

	@Bean
	String myBean() {

		return "This is a Spring bean!";
	}

}

package org.paradise;

import io.hawt.springboot.EnableHawtio;
import io.hawt.web.AuthenticationFilter;
import org.apache.camel.spring.boot.FatJarRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableHawtio
public class MainSpringBootCamelRouter extends FatJarRouter {

	private static final Logger logger = LoggerFactory.getLogger(MainSpringBootCamelRouter.class);

	public static void main(String[] args) {

		System.setProperty(AuthenticationFilter.HAWTIO_AUTHENTICATION_ENABLED, "false");

		logger.debug("Start Spring Boot Camel Router Service ...");

		SpringApplication.run(MainSpringBootCamelRouter.class, args);

		logger.debug("Happy Spring Boot Camel Router Service ...");
	}

	@Override
	public void configure() {

		from("timer:trigger")
				// hard code break point
				.process(exchange -> System.out.println())
				// Add the following line before a bean, for example:
				//
				//   .to("file:///Users/terrence/Projects/pcc")
				//   .to("cxf:bean:labelPrintServiceEndpoint?dataFormat=POJO")
				//
				// under "/Users/terrence/Projects/pcc" directory, HTTP/SOAP request will put into file e.g.
                // "ID-muffler-53122-1459400952912-0-11"
				.transform().simple("ref:myBean")
				.to("log:out");
	}

	@Bean
	String myBean() {

		return "This is a beautiful Spring Bean!";
	}

}

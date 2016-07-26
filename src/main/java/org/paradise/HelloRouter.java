package org.paradise;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.stereotype.Component;

/**
 * Created by terrence on 26/07/2016.
 */
@Component
public class HelloRouter extends FatJarRouter {

    @Override
    public void configure() {

        // configure to use jetty on localhost with the given port and enable auto binding mode
        restConfiguration()
                .component("jetty")
                .host("localhost").port(9090)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES, ADJUST_DATES_TO_CONTEXT_TIME_ZONE")
                .dataFormatProperty("json.in.enableFeatures", "FAIL_ON_NUMBERS_FOR_ENUMS, USE_BIG_DECIMAL_FOR_FLOATS")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "API")
                .apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true");

        rest("/say")
                .description("Say hello")
                .consumes("application/json")
                .produces("application/json")
                .get("/hello")
                .route()
                .routeId("Hello REST")
                .transform().constant("Say hello to REST!");
    }

}
package org.paradise.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by terrence on 22/06/2016.
 */
@RestController
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String home() {

        LOG.debug("Homie homie ...");

        return "forward:/hystrix";
    }

}

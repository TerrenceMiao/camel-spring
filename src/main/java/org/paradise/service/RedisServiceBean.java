package org.paradise.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by terrence on 24/07/2016.
 */
@Component
public class RedisServiceBean {

    private static final Logger LOG = LoggerFactory.getLogger(RedisServiceBean.class);

    public String getMessage(String message) {

        LOG.debug("Redis message is: " + message);

        return message;
    }

    public void handleException(String exceptionMessage) {

        LOG.error("Exception thrown while invoke Redis service: " + exceptionMessage);

    }

}

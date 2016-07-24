package org.paradise.service;

import org.paradise.Constants;
import org.paradise.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by terrence on 24/07/2016.
 */
@Component
public class RedisService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisService.class);

    private RedisRepository redisRepository;

    @Autowired
    public RedisService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public void getMessage(String message) {

        redisRepository.saveHash("RedisMessage", message);

        LOG.debug("Redis message is: " + redisRepository.findHash("RedisMessage"));
        LOG.debug("Redis HSET value is: [" + redisRepository.findHash(Constants.REDIS_FIELD) + "]");
    }

    public void handleException(String exceptionMessage) {

        LOG.error("Exception thrown while invoke Redis service: " + exceptionMessage);
    }

}

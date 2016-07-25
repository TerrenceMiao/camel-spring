package org.paradise;

import org.apache.camel.Processor;
import org.apache.camel.component.redis.RedisConstants;
import org.apache.camel.spring.boot.FatJarRouter;
import org.paradise.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created by terrence on 25/07/2016.
 */
@Component
public class RedisRouter extends FatJarRouter {

    private static final Processor enrichExchangeBody
            = exchange -> exchange.getIn().setBody("[" + exchange.getIn().getBody().toString() + "]");

    @Autowired
    RedisService redisService;

    @Override
    public void configure() {

        // Camel Redis publish
        from("timer://redis?period=20s")
                .routeId("Redis publisher")
                .onException(JedisConnectionException.class)
                .handled(true)
                .transform().simple("${exception.message}")
                .bean(redisService, "handleException(${body})")
                .to("mock:error")
                .end()
                // for publish and subscribe
                .setHeader(RedisConstants.CHANNEL, constant("camelChannel"))
                .setHeader(RedisConstants.COMMAND, constant("PUBLISH"))
                .setHeader(RedisConstants.MESSAGE, constant("This is hello message from Camel to Redis"))
                .to("spring-redis://localhost:6379")
                // for key / value caching
                .setHeader(RedisConstants.COMMAND, constant("HSET"))
                .setHeader(RedisConstants.KEY, constant(Constants.REDIS_KEY))
                .setHeader(RedisConstants.FIELD, constant(Constants.REDIS_FIELD))
                .setHeader(RedisConstants.VALUE, constant("This is Redis HSET value"))
                .to("spring-redis://localhost:6379?redisTemplate=#redisTemplate");

        // Camel Redis subscribe
        from("spring-redis://localhost:6379?command=SUBSCRIBE&channels=camelChannel&redisTemplate=#redisTemplate")
                .routeId("Redis subscriber")
                .process(enrichExchangeBody)
                .bean(redisService, "getMessage(${body})")
                .to("mock:result");
    }

}

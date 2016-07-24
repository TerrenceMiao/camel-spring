package org.paradise.repository;

import org.paradise.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by terrence on 24/07/2016.
 */
@Repository
public class RedisRepository {

    private RedisTemplate<String, String> redisTemplate;

    private HashOperations hashOps;

    @Autowired
    public RedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public void saveHash(String hashKey, Object value) {
        hashOps.put(Constants.REDIS_KEY, hashKey, value);
    }

    public void updateHash(String hashKey, Object value) {
        hashOps.put(Constants.REDIS_KEY, hashKey, value);
    }

    public Object findHash(String hashKey) {
        return hashOps.get(Constants.REDIS_KEY, hashKey);
    }

    public Map<Object, Object> findAllHash() {
        return hashOps.entries(Constants.REDIS_KEY);
    }

    public void deleteHash(String hashKey) {
        hashOps.delete(Constants.REDIS_KEY, hashKey);
    }

}

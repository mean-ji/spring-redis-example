package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setStringOps(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getStringOps(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setListOps(String key, List<Object> values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    public List<Object> getListOps(String key) {
        Long len = redisTemplate.opsForList().size(key);
        return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len - 1);
    }
}

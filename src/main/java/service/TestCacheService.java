package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import domain.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private RedisService redisService;
    final ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(TestCacheService.class);

//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public TestCacheService(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }


    //    @Cacheable(cacheNames="userCache")
    public void setUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("민지", 21));
        users.add(new User("서윤", 22));
        users.add(new User("동진", 23));

        ListOperations<String, Object> redisList = redisTemplate.opsForList();
        ValueOperations<String, Object> redisValue = redisTemplate.opsForValue();

        int i = 0;
        for (User user : users) {
            redisValue.set("users:user"+i, user);
//            redisList.set("users", i, user);
//            redisList.rightPush("users", user);
            i++;
        }


        Map<String, Object> redMap = new HashMap<>();
        redMap.put("users", users);

//        redisTemplate.opsForValue().set("users", "민지");

//        return users;
    }

    public Object getUsers() throws ParseException {
//        String key = "users";
//        long size = stringRedisTemplate.opsForList().size(key);
//        System.out.println(size);
//        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
//
//        List<Object> resList = new ArrayList<>();
//        for (String listStr : list) {
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(listStr);
//            resList.add(jsonObject);
//        }
//        System.out.println(resList);



        return stringRedisTemplate.opsForValue().get("users:user1");
    }

    @Caching(evict = {
            @CacheEvict(cacheNames="userCache", allEntries = true)
    })
    public void removeCacheUsers(){
    }
}

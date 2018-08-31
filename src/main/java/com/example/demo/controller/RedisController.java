package com.example.demo.controller;

import com.example.demo.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    private ValueOperations jedis;

    @RequestMapping("/redis")
    public void redis() {
        initJedis();
        redisString();
        redisHash();
        redisList();
    }

    public void initJedis(){
        jedis = redisTemplate.opsForValue();
    }

    //redis字符串操作----------------------------------------------
    public void redisString(){
        System.out.println("------------------this is redis string test---------------------");
        jedis.set("name","hello redis");
        System.out.println("redis设置了属性name,值为："+jedis.get("name"));
        jedis.append("name",",this is append string");
        System.out.println("为属性name追加了string，追加后的值为："+jedis.get("name"));
        jedis.set("name","this is reset");
        System.out.println("重置了redis的name属性,重置后的值为："+jedis.get("name"));
        redisTemplate.delete("name");
        System.out.println("redis删除了name属性，重新获取name值："+jedis.get("name"));
    }
    // redis哈希操作------------------------------------------------
    public void redisHash(){
        System.out.println("------------------this is redis hash test---------------------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("name", "yc");
        map.put("age", "22");
        map.put("qq", "1933108196");
        redisTemplate.opsForHash().putAll("user",map);
        Collection collect = new ArrayList(Arrays.asList("name","qq","age"));
        List list = redisTemplate.opsForHash().multiGet("user",collect);
        System.out.println("为对象user赋hash属性，user的name属性值为："+list);
        redisTemplate.opsForHash().delete("user","name","qq");
        List list1 = redisTemplate.opsForHash().multiGet("user",collect);
        System.out.println("删除对象user的name，qq属性，user的name属性值为："+list1);
        Iterator<String> it = redisTemplate.opsForHash().keys("user").iterator();
        while(it.hasNext()){
            String key = it.next();
            System.out.println("获取对象user的"+key+"属性，属性值为："+redisTemplate.opsForHash().get("user",key));
        }
    }

    public void redisList(){
        System.out.println("------------------this is redis list test---------------------");

    }

}

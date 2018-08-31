package com.example.demo.controller;

import com.example.demo.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/redis")
    public void redis() {
        redisString();
        redisHash();
        redisList();
        redisSet();
        redisZSet();
    }

    //redis字符串操作----------------------------------------------
    public void redisString(){
        System.out.println("------------------this is redis string test---------------------");
        redisTemplate.opsForValue().set("name","hello redis");
        System.out.println("redis设置了属性name,值为："+redisTemplate.opsForValue().get("name"));
        redisTemplate.opsForValue().append("name",",this is append string");
        System.out.println("为属性name追加了string，追加后的值为："+redisTemplate.opsForValue().get("name"));
        redisTemplate.opsForValue().set("name","this is reset");
        System.out.println("重置了redis的name属性,重置后的值为："+redisTemplate.opsForValue().get("name"));
        redisTemplate.delete("name");
        System.out.println("redis删除了name属性，重新获取name值："+redisTemplate.opsForValue().get("name"));
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
        redisTemplate.delete("java framework");
        System.out.println("java framework对象已置空，置空后结果为："+redisTemplate.opsForList().range("java framework",0,-1));
        redisTemplate.opsForList().leftPush("java framework","spring");
        redisTemplate.opsForList().leftPush("java framework","struts");
        redisTemplate.opsForList().leftPush("java framework","hibernate");
        System.out.println("java framework对象添加三条记录，结果为："+redisTemplate.opsForList().range("java framework",0,-1));
        redisTemplate.delete("java framework");
        System.out.println("java framework对象已置空，置空后结果为："+redisTemplate.opsForList().range("java framework",0,-1));
        redisTemplate.opsForList().rightPush("java framework","spring");
        redisTemplate.opsForList().rightPush("java framework","struts");
        redisTemplate.opsForList().rightPush("java framework","hibernate");
        System.out.println("java framework对象添加三条记录，结果为："+redisTemplate.opsForList().range("java framework",0,-1));
        redisTemplate.opsForList().leftPop("java framework");
        System.out.println("java framework左边出栈，结果为："+redisTemplate.opsForList().range("java framework",0,-1));
        System.out.println("java framework取出第二个数，结果为："+redisTemplate.opsForList().index("java framework",1));
    }

    public void redisSet(){
        System.out.println("------------------this is redis set test---------------------");
        redisTemplate.opsForSet().add("grade1","zhangli","liling","wangai");
        redisTemplate.opsForSet().add("grade2","zhangli","liling","wangbeibei");
        System.out.println("set集合，为grade添加属性，集合值为："+redisTemplate.opsForSet().members("grade1"));
        System.out.println("set集合，grade不同项，difference："+redisTemplate.opsForSet().difference("grade1","grade2"));
        System.out.println("set集合，grade相同项，intersect:"+redisTemplate.opsForSet().intersect("grade1","grade2"));
        redisTemplate.delete("grade1");
        System.out.println("删除grade1,删除后grade1输出："+redisTemplate.opsForSet().members("grade1"));
        System.out.println("删除grade1,删除后grade2输出："+redisTemplate.opsForSet().members("grade2"));
        System.out.println("删除grade1,删除后student输出："+redisTemplate.opsForSet().members("students"));
    }

    public void redisZSet(){
        System.out.println("------------------this is redis zset test---------------------");
        redisTemplate.opsForZSet().add("girls","diaochan",2);
        redisTemplate.opsForZSet().add("girls","wangzhaojun",1);
        System.out.println("有序集合结果输出："+redisTemplate.opsForZSet().range("girls",0,3));
        System.out.println("有序集合元素个数："+redisTemplate.opsForZSet().size("girls"));
    }

}

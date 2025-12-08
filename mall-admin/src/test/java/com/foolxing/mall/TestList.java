package com.foolxing.mall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
public class TestList {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 右压栈：后添加的对象排在后边
     */
    @Test
    public void testSetValue1(){
        redisTemplate.opsForList().rightPush("nameList1", "刘备");
        redisTemplate.opsForList().rightPush("nameList1", "关羽");
        redisTemplate.opsForList().rightPush("nameList1", "张飞");
    }

    /**
     * 显示右压栈集合
     */
    @Test
    public void testGetValue1(){
        // List list = redisTemplate.opsForList().range("nameList1", 0, 10);
        List list = redisTemplate.opsForList().range("nameList1", 0, -1);
        System.out.println(list);
    }

    /**
     * 左压栈：后添加的对象排在前边
     */
    @Test
    public void testSetValue2(){
        redisTemplate.opsForList().leftPush("nameList2", "刘备");
        redisTemplate.opsForList().leftPush("nameList2", "关羽");
        redisTemplate.opsForList().leftPush("nameList2", "张飞");
    }

    /**
     * 显示左压栈集合
     */
    @Test
    public void testGetValue2(){
        List list = redisTemplate.opsForList().range("nameList2", 0, -1);
        System.out.println(list);
    }

    /**
     * 查询集合某个元素
     */
    @Test
    public void testSearchByIndex(){
        String s = (String) redisTemplate.opsForList().index("nameList1", 1);
        System.out.println(s);
    }

    /**
     * 移除集合某个元素
     */
    @Test
    public void testRemoveByIndex(){
        redisTemplate.opsForList().remove("nameList1", 1, "关羽");
    }
}
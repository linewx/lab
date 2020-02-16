package com.zz.lab.cache;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RedisTest {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void testRedisValue() {
        redisTemplate.delete("name");
        redisTemplate.delete("age");

        redisTemplate.boundValueOps("name").set("tony");
        redisTemplate.boundValueOps("age").set(20);

        Assert.assertEquals(redisTemplate.boundValueOps("name").get(), "tony");
        Assert.assertEquals(redisTemplate.boundValueOps("age").get(), 20);

        redisTemplate.boundValueOps("age").set(21);
        Assert.assertEquals(redisTemplate.boundValueOps("age").get(), 21);

        redisTemplate.delete("age");
        Assert.assertEquals(redisTemplate.boundValueOps("age").get(), null);
    }

    @Test
    public void testRedisHash() {
        redisTemplate.delete("score");
        redisTemplate.boundHashOps("score").put("c", 90);
        redisTemplate.boundHashOps("score").put("java", 99);
        redisTemplate.boundHashOps("score").put("math", 90);

        Assert.assertEquals(redisTemplate.boundHashOps("score").size(), Long.valueOf(3));
        Assert.assertEquals(99, redisTemplate.boundHashOps("score").get("java"));

        redisTemplate.boundHashOps("score").delete("math");
        Assert.assertEquals(redisTemplate.boundHashOps("score").size(), Long.valueOf(2));
    }

    @Test
    public void testRedisList() {
        redisTemplate.delete("company");
        redisTemplate.boundListOps("company").leftPush("hp");
        redisTemplate.boundListOps("company").leftPush("hpe");
        redisTemplate.boundListOps("company").leftPush("hpe");
        redisTemplate.boundListOps("company").leftPush("msxw");
        Assert.assertEquals(redisTemplate.boundListOps("company").size(), Long.valueOf(4));
        Assert.assertEquals(redisTemplate.boundListOps("company").rightPop(), "hp");
        Assert.assertEquals(redisTemplate.boundListOps("company").leftPop(), "msxw");
    }

    @Test
    public void testRedisSet() {
        redisTemplate.delete("companies");
        redisTemplate.boundSetOps("companies").add("hp");
        redisTemplate.boundSetOps("companies").add("hpe");
        redisTemplate.boundSetOps("companies").add("hpe");
        redisTemplate.boundSetOps("companies").add("msxw");
        Assert.assertEquals(redisTemplate.boundSetOps("companies").size(), Long.valueOf(3));
        redisTemplate.boundSetOps("companies").remove("msxw");
        Assert.assertEquals(redisTemplate.boundSetOps("companies").size(), Long.valueOf(2));
        Assert.assertTrue(redisTemplate.boundSetOps("companies").isMember("hpe"));
        Assert.assertFalse(redisTemplate.boundSetOps("companies").isMember("ebay"));

    }

    @Test
    public void testRedisZset() {
        redisTemplate.delete("skill");
        redisTemplate.boundZSetOps("skill").add("java", 90);
        redisTemplate.boundZSetOps("skill").add("python", 80);
        redisTemplate.boundZSetOps("skill").add("redis", 60);
        redisTemplate.boundZSetOps("skill").add("kafka", 70);
        redisTemplate.boundZSetOps("skill").add("mysql", 75);

        Assert.assertEquals(redisTemplate.boundZSetOps("skill").size(), Long.valueOf(5));

        Assert.assertEquals(redisTemplate.boundZSetOps("skill").count(80, 90), Long.valueOf(2));
        Assert.assertEquals(redisTemplate.boundZSetOps("skill").count(80, 85), Long.valueOf(1));
        Set<Object> skills = redisTemplate.boundZSetOps("skill").rangeByScore(60, 70);
        Assert.assertTrue(skills.contains("redis"));
        Assert.assertTrue(skills.contains("kafka"));
    }

    @Test
    public void testRedisGeo() {
        //地理信息
        redisTemplate.boundGeoOps("cities").add(new Point(116.28,39.55), "beijing");
        redisTemplate.boundGeoOps("cities").add(new Point(121.48,31.22), "shanghai");
        redisTemplate.boundGeoOps("cities").distance("shanghai", "beijing").getMetric().getAbbreviation();
        //单位是m
        Assert.assertEquals("m", redisTemplate.boundGeoOps("cities").distance("shanghai", "beijing").getUnit());

        double distance = redisTemplate.boundGeoOps("cities").distance("shanghai", "beijing").getValue();
        //distance between shanghai & beijing, should be between 1000 ~ 1200
        Assert.assertTrue(distance > 1000 * 1000);
        Assert.assertTrue(distance < 1200 * 1000);
    }

    @Test
    public void testRedisHyperLogLog() {
        redisTemplate.opsForHyperLogLog().add("job1", "hp", "hpe");
        redisTemplate.opsForHyperLogLog().add("job2", "microfocus", "msxw");
        System.out.println(redisTemplate.opsForHyperLogLog().size("obj1"));
    }

    @Test
    public void testRedisPubSub() {
        //pub sub model test
        redisTemplate.convertAndSend("topic", "hello guys");
    }


}

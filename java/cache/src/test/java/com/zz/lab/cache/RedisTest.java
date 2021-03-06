package com.zz.lab.cache;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RedisTest {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisValue() throws Exception {
        redisTemplate.delete("name");
        redisTemplate.delete("name2");
        redisTemplate.delete("nam3");
        redisTemplate.delete("age");


        redisTemplate.boundValueOps("name").set("tony");
        redisTemplate.boundValueOps("age").set(20);

        Assert.assertEquals(redisTemplate.boundValueOps("name").get(), "tony");
        Assert.assertEquals(redisTemplate.boundValueOps("age").get(), 20);

        redisTemplate.boundValueOps("age").set(21);
        Assert.assertEquals(redisTemplate.boundValueOps("age").get(), 21);

        redisTemplate.delete("age");
        Assert.assertEquals(redisTemplate.boundValueOps("age").get(), null);

        redisTemplate.boundValueOps("valid").set(true, 1, TimeUnit.SECONDS);
        Assert.assertEquals(redisTemplate.boundValueOps("valid").get(), true);

        Thread.sleep(2000);
        Assert.assertNull(redisTemplate.boundValueOps("valid").get());

        //test keys function
        redisTemplate.boundValueOps("name2").set("Allen");
        redisTemplate.boundValueOps("name3").set("Jack");
        Set<Object> keys = redisTemplate.keys("name*");
        System.out.println(keys);
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
        Assert.assertEquals(redisTemplate.boundListOps("company").size(), Long.valueOf(2));

        redisTemplate.boundListOps("company").rightPop(2, TimeUnit.SECONDS);
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

    @Test
    public void testScan() {
        redisTemplate.delete("name1");
        redisTemplate.delete("name2");
        redisTemplate.delete("name3");
        redisTemplate.boundValueOps("name1").set("tony");
        redisTemplate.boundValueOps("name2").set("tom");
        redisTemplate.boundValueOps("name3").set("allen");

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.execute((RedisConnection connection) -> {
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(1).match("*name*").build());
            cursor.forEachRemaining(x -> {
                System.out.println(redisSerializer.deserialize(x));
            });
            return null;
        });
    }

    @Test
    public void testRedisPipeline() {
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();

        List<Object> results = redisTemplate.executePipelined((RedisConnection redisConnection) -> {
            redisConnection.del("name".getBytes(), "age".getBytes());
            redisConnection.set("name".getBytes(), serializer.serialize("tom"));
            redisConnection.set("age".getBytes(), serializer.serialize(21));
            redisConnection.get("name".getBytes());
            redisConnection.get("age".getBytes());
            return null;
        });

        Assert.assertEquals(results.get(results.size() - 2), "tom");
        Assert.assertEquals(results.get(results.size() - 1), 21);
        System.out.println(results);
    }

    @Test
    public void testRedisTransaction() {
        redisTemplate.delete("name");
        redisTemplate.boundValueOps("name").set("tom");
        redisTemplate.delete("age");
        redisTemplate.boundValueOps("age").set(21);

        List<Object> results = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.watch("name");
                operations.multi();
                operations.boundValueOps("name").set("tony");
                operations.boundValueOps("age").set(23);

                operations.boundValueOps("name").get();
                operations.boundValueOps("age").get();
                return operations.exec();
            }
        });

        Assert.assertEquals(results.get(0), true);
        Assert.assertEquals(results.get(1), true);
        Assert.assertEquals(results.get(2), "tony");
        Assert.assertEquals(results.get(3), 23);


        System.out.println(results);
    }


}

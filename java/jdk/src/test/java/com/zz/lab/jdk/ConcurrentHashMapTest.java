package com.zz.lab.jdk;

import org.junit.Test;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    @Test
    public void test() {

        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        map.put("6", "1");
        map.put("7", "1");
        map.put("8", "1");
        map.put("11", "1");
        map.put("12", "1");
        map.put("13", "1");
        map.put("14", "1");
        map.put("15", "1");
        map.put("16", "1");
        map.put("17", "1");
        map.put("18", "1");

        map.put("21", "1");
        map.put("22", "1");
        map.put("23", "1");
        map.put("24", "1");
        map.put("25", "1");
        map.put("26", "1");
        map.put("27", "1");
        map.put("28", "1");
        map.put("211", "1");
        map.put("212", "1");
        map.put("213", "1");
        map.put("214", "1");
        map.put("215", "1");
        map.put("216", "1");
        map.put("217", "1");
        map.put("218", "1");

    }

}

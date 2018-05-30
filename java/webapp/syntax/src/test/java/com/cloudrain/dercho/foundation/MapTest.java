package com.cloudrain.dercho.foundation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * Created by lugan on 5/13/2016.
 */
public class MapTest {
    Map<String, String> fixture;

    @Before
    public void setup() {
        fixture = new HashMap<>();
        fixture.put("name", "rain");
        fixture.put("email", "linewx1981@gmail.com");
        fixture.put("education", "master");
        fixture.put("sex", "male");
        fixture.put("company", "hpe");

    }

    @Test
    public void containsKeysTest() {
        Assert.assertTrue(fixture.containsKey("name"));
        Assert.assertFalse(fixture.containsKey("address"));
    }

    @Test
    public void containsValueTest() {
        Assert.assertTrue(fixture.containsValue("rain"));
        Assert.assertFalse(fixture.containsValue("qibao"));
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(fixture.size(), 5);
    }

    @Test
    public void isEmptyTest(){
        Assert.assertFalse(fixture.isEmpty());
        fixture.clear();
        Assert.assertTrue(fixture.isEmpty());
    }

    @Test
    public void clearTest() {
        Assert.assertFalse(fixture.isEmpty());
        fixture.clear();
        Assert.assertTrue(fixture.isEmpty());
    }

    @Test
    public void getTest() {
        Assert.assertNotNull(fixture.get("name"));
        Assert.assertNull(fixture.get("address"));
    }

    @Test
    public void putTest() {
        fixture.put("address", "qibao");
        Assert.assertEquals(fixture.size(), 6);
        Assert.assertEquals(fixture.get("address"), "qibao");
    }

    @Test
    public void removeKeyTest() {
        Assert.assertNotNull(fixture.get("education"));
        fixture.remove("education");
        Assert.assertNull(fixture.get("education"));
    }

    @Test
    public void removeEntryTest() {
        Assert.assertNotNull(fixture.get("education"));
        Assert.assertTrue(fixture.remove("education", "master"));
        Assert.assertNull(fixture.get("education"));

        Assert.assertFalse(fixture.remove("company", "hp"));
        Assert.assertNotNull(fixture.get("company"));

        Assert.assertFalse(fixture.remove("company1", "hp"));
    }

    @Test
    public void putAllTest() {
        Map<String, String> newItems = new HashMap<>();
        newItems.put("address", "qibao");
        newItems.put("company", "Ali");
        fixture.putAll(newItems);
        Assert.assertEquals(fixture.size(), 6);
        Assert.assertEquals(fixture.get("address"), "qibao");
        Assert.assertEquals(fixture.get("company"), "Ali");
    }

    @Test
    public void keySetTest() {
        Set<String> keys = fixture.keySet();
        Assert.assertTrue(keys.contains("name"));
        Assert.assertFalse(keys.contains("address"));
        fixture.put("address", null);
        keys = fixture.keySet();
        Assert.assertTrue(keys.contains("address"));
    }

    @Test
    public void valuesTest() {
        Collection<String> values = fixture.values();
        Assert.assertTrue(values.contains("rain"));
        Assert.assertFalse(values.contains("qibao"));
    }

    @Test
    public void getOrDefaultTest() {
        Assert.assertEquals(fixture.getOrDefault("address", "unknown"), "unknown");
    }

    @Test
    public void forEachTest(){
        Collection<String> items = new ArrayList<>();
        fixture.forEach((x,y) -> items.add(x+"-"+y));
        Assert.assertTrue(items.contains("name-rain"));
    }

    @Test
    public void replaceAllTest() {
        fixture.replaceAll((x,y) -> x+"-"+y);
        Assert.assertTrue(fixture.containsValue("name-rain"));
        Assert.assertFalse(fixture.containsKey("name-rain"));
    }

    @Test
    public void putIfAbsentTest() {
        fixture.putIfAbsent("address", "qibao");
        fixture.putIfAbsent("company", "hp");
        Assert.assertEquals(fixture.get("address"), "qibao");
        Assert.assertNotEquals(fixture.get("company"), "hp");
    }

    @Test
    public void replaceTest() {
        Assert.assertEquals(fixture.replace("company", "hp"), "hpe");
        Assert.assertEquals(fixture.get("company"), "hp");
        Assert.assertNull(fixture.replace("address", "qibao"));
        Assert.assertNull(fixture.get("address"));
        fixture.put("address", null);
        Assert.assertNull(fixture.replace("address", "qibao"));
        Assert.assertNotNull(fixture.get("address"));
    }

    @Test
    public void replaceOldTest() {
        Assert.assertFalse(fixture.replace("company", "hp", "hpe"));
        Assert.assertEquals(fixture.get("company"), "hpe");
        Assert.assertTrue(fixture.replace("company", "hpe", "hp"));
        Assert.assertEquals(fixture.get("company"), "hp");
    }

    @Test
    public void computeIfAbsentTest() {
        String result = fixture.computeIfAbsent("address", x->"qibao");
        Assert.assertEquals(result, "qibao");
        Assert.assertEquals(fixture.get("address"), "qibao");
    }

    @Test
    public void computeTest() {
        String result = fixture.compute("address", (x,y)->"qibao");
        Assert.assertEquals(result, "qibao");
        Assert.assertEquals(fixture.get("address"), "qibao");
    }

    @Test
    public void computeIfPresentTest() {
        String result = fixture.computeIfPresent("address", (x,y)->"qibao");
        Assert.assertNull(result);
        Assert.assertNull(fixture.get("address"));
    }

    @Test
    public void mergeTest() {
        fixture.merge("email", "!", String::concat);
        fixture.forEach((x,y)->System.out.println(x+y));
    }


}

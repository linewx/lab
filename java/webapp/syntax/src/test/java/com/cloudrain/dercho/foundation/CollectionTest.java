package com.cloudrain.dercho.foundation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lugan on 5/11/2016.
 */
public class CollectionTest {
    private Collection<String> fixture;

    @Before
    public void setup() {
        fixture = new ArrayList<>(Arrays.asList("java", "javascript", "python", "c++", "bash", "javascript"));
    }

    @Test
    public void addTest() {
        fixture.add("groovy");
        Assert.assertEquals(fixture.size(), 7);
    }

    @Test
    public void addAllTest() {
        Collection<String> toAddCollections = new ArrayList<>(Arrays.asList("groovy", "scala"));
        fixture.addAll(toAddCollections);
        Assert.assertEquals(fixture.size(), 8);
    }

    @Test
    public void removeTest() {
        Assert.assertTrue(fixture.remove("c++"));
        Assert.assertEquals(fixture.size(), 5);
    }

    @Test
    public void removeAllTest() {
        Collection<String> toRemoveCollection = new ArrayList<>(Arrays.asList("javascript", "groovy"));
        fixture.removeAll(toRemoveCollection);
        Assert.assertEquals(fixture.size(), 4);
    }

    @Test
    public void removeIfTest() {
        fixture.removeIf(x->((x.length() -4) > 0));
        Assert.assertFalse(fixture.contains("javascript"));
        Assert.assertTrue(fixture.contains("java"));
    }



//    @Test
//    public void d() {
//        fixture.
//    }



    @Test
    public void sizeTest(){
        Assert.assertEquals(fixture.size(), 6);
    }

    @Test
    public void isEmptyTest() {
        Assert.assertFalse(fixture.isEmpty());
    }

    @Test
    public void clearTest() {
        Assert.assertFalse(fixture.isEmpty());
        fixture.clear();
        Assert.assertTrue(fixture.isEmpty());
    }

    @Test
    public void containsTest() {
        Assert.assertTrue(fixture.contains("java"));
        Assert.assertFalse(fixture.contains("groovy"));
    }

    @Test
    public void containsAllTest() {
        Collection<String> first = new ArrayList<>(Arrays.asList("java", "python"));
        Collection<String> second = new ArrayList<>(Arrays.asList("groovy", "java"));
        Assert.assertTrue(fixture.containsAll(first));
        Assert.assertFalse(fixture.containsAll(second));
    }

    @Test
    public void iteratorTest() {
        int count = 0;
        for (String one : fixture) {
            count ++;
        }
        Assert.assertEquals(count, 6);
    }



    @Test
    public void ParallelStreamTest(){
        //todo: to implement
    }

    @Test
    public void streamTest() {
        List<String> sortedCollection = fixture.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(sortedCollection.indexOf("bash"), 0);
    }

    @Test
    public void toArrayTest() {
        Object[] strArrays = fixture.toArray();
        Assert.assertEquals(strArrays.length, 6);
    }

    @Test
    public void toTArrayTest() {
        String[] strArrays = fixture.toArray(new String[0]);
        Assert.assertEquals(strArrays.length, 6);
    }


}

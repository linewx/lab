package com.cloudrain.dercho.foundation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by lugan on 5/11/2016.
 */
public class ListTest {

    private List<String> fixture;

    @Before
    public void setup() {
        fixture = new ArrayList<>(Arrays.asList("java", "javascript", "python", "c++", "bash", "javascript"));
    }

    @Test
    public void autoboxTest() {
        List<Integer> intList = new ArrayList<>(Arrays.asList(5,4,3,2,1));
        intList.remove(new Integer(1));
        intList.forEach(System.out::println);
    }

    @Test
    public void addAllTest() {
        List<String> newSkills = new LinkedList<>(Arrays.asList("scala", "groovy"));
        fixture.addAll(2, newSkills);
        Assert.assertEquals(fixture.indexOf("scala"), 2);
    }

    @Test
    public void replaceAllTest() {
        fixture.replaceAll(t -> t + " programing language");
        Assert.assertTrue(fixture.indexOf("java programing language") != -1);
    }

    @Test
    public void sortTest() {
        fixture.sort(String::compareTo);
        Assert.assertTrue(fixture.indexOf("bash") == 0);
    }

    @Test
    public void getTest() {
        Assert.assertEquals(fixture.get(1), "javascript");
    }

    @Test
    public void setTest() {
        fixture.set(3, "scala");
        Assert.assertEquals(fixture.size(), 6);
        Assert.assertEquals(fixture.get(3), "scala");
    }

    @Test
    public void addTest() {
        fixture.add(3, "scala");
        Assert.assertEquals(fixture.size(), 7);
        Assert.assertEquals(fixture.get(3), "scala");
    }

    @Test
    public void removeTest() {
        fixture.remove(0);
        Assert.assertEquals(fixture.size(), 5);
        Assert.assertEquals(fixture.indexOf("java") , -1);
    }

    @Test
    public void indexOfTest() {
        Assert.assertEquals(fixture.indexOf("javascript"), 1);
    }

    @Test
    public void lastIndexOfTest() {
        Assert.assertEquals(fixture.lastIndexOf("javascript"), 5);
    }

    @Test
    public void subListTest() {
        List<String> subList = fixture.subList(0, 5);
        Assert.assertEquals(subList.size(), 5);
        subList.remove(0);
        Assert.assertEquals(fixture.size(), 5);
    }

    @Test
    public void listIteratorTest() {
        ListIterator<String> iter = fixture.listIterator();
        int count = 0;
        while(iter.hasNext()) {
            iter.next();
            count ++;
        }

        Assert.assertEquals(count, fixture.size());

    }
}

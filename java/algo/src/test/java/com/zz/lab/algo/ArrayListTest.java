package com.zz.lab.algo;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    @Test
    public void testArrayList() {
        List<String> names = new ArrayList<>();
        verifyList(names);
    }

    @Test
    public void testMyArrayList() {
        List<String> names = new MyArrayList<>();
        verifyList(names);
    }

    public  void verifyList(List<String> names) {
        Assert.assertTrue(names.isEmpty());
        names.add("Tom");
        names.add("Tony");
        names.add("Hellen");

        Assert.assertNotEquals(names.isEmpty(), true);
        Assert.assertEquals(names.size(), 3);

        Assert.assertEquals("Tony", names.get(1));
        names.set(1, "Bruno");
        Assert.assertEquals("Bruno", names.get(1));
        names.remove(0);
        Assert.assertEquals("Hellen", names.get(1));
        Assert.assertEquals(2, names.size());
    }

}

package com.cloudrain.dercho.foundation;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by lugan on 5/18/2016.
 */
public class SortedSetTest {
    private SortedSet<String> fixture;
    @Before
    public void setup() {
        fixture = new TreeSet<>();
        fixture.addAll(Arrays.asList("java", "python", "groovy","c++", "javascript"));
    }

    @Test
    public void firstTest() {
        Assert.assertEquals(fixture.first(), "c++");
    }

    @Test
    public void lastTest() {
        Assert.assertEquals(fixture.last(), "python");
    }

    @Test
    public void headSetTest() {
        //Set<String> headSet = fixture.headSet("groovy")
    }

}

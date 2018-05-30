package com.cloudrain.dercho.foundation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by lugan on 5/18/2016.
 */
public class QueueTest {
    private Queue<String> fixture;
    @Before
    public void setup() {
        fixture = new PriorityQueue<>(3);
        fixture.add("java");
        fixture.add("python");
    }

    @Test
    public void addTest() {
        Assert.assertTrue(fixture.add("groovy"));
        Assert.assertTrue(fixture.contains("groovy"));
    }

    @Test
    public void offerTest() {
        Assert.assertTrue(fixture.offer("groovy"));
        Assert.assertTrue(fixture.contains("groovy"));
    }

    @Test
    public void removeTest() {
        Assert.assertEquals(fixture.remove(), "java");
        Assert.assertEquals(fixture.size(), 1);
    }

    @Test
    public void pollTest() {
        Assert.assertEquals(fixture.poll(), "java");
        Assert.assertEquals(fixture.size(), 1);
    }

    @Test
    public void elementTest() {
        Assert.assertEquals(fixture.element(), "java");
        Assert.assertEquals(fixture.size(), 2);
    }

    @Test
    public void peekTest() {
        Assert.assertEquals(fixture.peek(), "java");
        Assert.assertEquals(fixture.size(), 2);
    }

}

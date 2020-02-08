package com.zz.lab.algo;


import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ArrayQueueTest {
    @Test
    public void test() {
        Deque<String> names = new ArrayDeque<>();
        verify(names);
    }

    @Test
    public void testMyArrayList() {
        Deque<String> names = new ArrayDeque<>();
        verify(names);
    }

    public void verify(Queue<String> names) {
        //add ~= offer,exception handler
        //remove ~= poll, exception handler
        names.clear();
        Assert.assertEquals(names.size(), 0);
        names.add("tony");
        Assert.assertEquals(names.size(), 1);
        names.offer("helen");
        Assert.assertEquals(names.size(), 2);
        Assert.assertEquals(names.peek(), "tony");
        names.remove();
        Assert.assertEquals(names.peek(), "helen");
        names.poll();
        Assert.assertNull(names.peek());

    }
}

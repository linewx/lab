package com.zz.lab.algo;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public class StaskTest {
    @Test
    public void test() {
        Stack<String> names = new Stack<>();
        verify(names);
    }

    public void verify(Stack<String> names) {
        Assert.assertTrue(names.empty());
        names.push("tom");
        names.push("tony");
        Assert.assertEquals(names.peek(), "tony");
        Assert.assertEquals(names.pop(), "tony");
        Assert.assertEquals(names.size(), 1);
        Assert.assertEquals(names.pop(), "tom");
        Assert.assertTrue(names.empty());


    }
}

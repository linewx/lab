package com.zz.lab.algo.heap;


import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;

public class HeapTest {
    @Test
    public void testHeap() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        verify(queue);
    }

    @Test
    public void testMyHeap() {
        MyHeap queue = new MyHeap();
        verify(queue);
    }

    private void verify(Queue<Integer> queue) {
        queue.add(3);
        queue.add(2);
        queue.add(5);
        queue.add(1);
        queue.add(9);
        Assert.assertEquals(queue.remove(), Integer.valueOf(1));
        Assert.assertEquals(queue.remove(), Integer.valueOf(2));
        Assert.assertEquals(queue.remove(), Integer.valueOf(3));
        Assert.assertEquals(queue.remove(), Integer.valueOf(5));
        Assert.assertEquals(queue.remove(), Integer.valueOf(9));
    }
}

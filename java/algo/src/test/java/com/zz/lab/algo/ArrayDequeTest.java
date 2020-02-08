package com.zz.lab.algo;


import org.junit.Test;

import java.util.*;

public class ArrayDequeTest {
    @Test
    public void testArrayList() {
        Deque<String> names = new ArrayDeque<>();
        verify(names);
    }

    @Test
    public void testMyArrayList() {
        Deque<String> names = new ArrayDeque<>();
        verify(names);
    }

    public  void verify(Deque<String> names) {

    }

    public void verifyQueue(Queue<String> names) {
        names.add("tony");
        names.add("joe");
        names.poll()
    }




}

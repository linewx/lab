package com.zz.playground.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luganlin on 24/06/2018.
 */
public class ThreadTest {

    @Test
    public void threadTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
    }
}

package com.zz.thread.basic;

import org.junit.Test;

import java.util.concurrent.Executor;

/**
 * Created by lugan on 8/9/2016.
 */
public class BasicTest {
    @Test
    public void ThreadTest() {
        new Thread(new ThreadExample()).start();
    }

//    @Test
//    public void ExecutorTest(){
//        Executor
//    }
}

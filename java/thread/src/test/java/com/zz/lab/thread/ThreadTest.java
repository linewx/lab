package com.zz.lab.thread;

import org.junit.Test;

public class ThreadTest {


    @Test
    public void testThead() {
        Thread one = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello thread");
        });

        one.start();
        try {
            one.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

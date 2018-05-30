package com.cloudrain.dercho.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lugan on 8/9/2016.
 */
public class BasicThread extends Thread{


    private static AtomicLong number = new AtomicLong(0l);

    @Override
    public void run() {
        for (long index=0; index<1000000; index++) {
            number.incrementAndGet();
        }
        System.out.println(number);
}

    public static void main(String []argv) {
        for (int index=0; index<100; index++) {
            new BasicThread().start();
        }

    }
}

package com.zz.lab.jvm;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * run with JProfiler, see thread tab: thread monitor, thread dump, thread history
 * thread is in blocked state
 * and main thread is in waiting thread
 */
public class TheadLockMonior {
    /**
     * 线程死锁等待演示
     */
    static class SynAddRunalbe implements Runnable {
        int a, b;
        public SynAddRunalbe(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    try {

                    Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                    System.out.println(a + b);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 100; i++) {
            new Thread(new SynAddRunalbe(1, 2)).start();
            new Thread(new SynAddRunalbe(2, 1)).start();
        }

        Thread.sleep(100000);
    }



}

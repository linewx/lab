package com.cloudrain.dercho.thread;

/**
 * Created by lugan on 8/10/2016.
 */
class NoVisibilityThread implements Runnable {
    volatile int num = 1000000;
    public void run() {
        if (Thread.currentThread().getName().equals("t1")) {
            increment();
        } else {
            decrement();
        }
    }

    public void increment() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
    }

    public void decrement() {
        for (int i = 0; i < 10000; i++) {
            num--;
        }
    }

    public static void main(String[] args) {
        NoVisibilityThread thread = new NoVisibilityThread();
        Thread a = new Thread(thread, "t1");
        Thread b = new Thread(thread, "t2");

        a.start();
        b.start();

        try {
            a.join();
            b.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(thread.num);
    }
}


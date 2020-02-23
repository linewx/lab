package com.zz.lab.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {
    @Test
    public void testThreadPool() {
        //test threadpool and future re
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int j = i;
            Callable<Integer> callable = () -> {
                try {
                    Thread.sleep(1000);
                    return j;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(j);
                return j;
            };
            Future<Integer> one = executorService.submit(callable);
            futures.add(one);

        }

        futures.forEach(x -> {
            try {
                System.out.println("result is " + x.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("finished");
    }

    @Test
    public void testThreadPoolExecute() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello world");
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread finished execution");

    }

    @Test
    public void testCompletionService() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            final int j = i;
            Callable<Integer> callable = () -> {
                try {
                    Thread.sleep(1000);
                    return j;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(j);
                return j;
            };
            Future<Integer> one = completionService.submit(callable);
            futures.add(one);
        }

        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("results" + completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCompletableFuture() {
        System.out.println("main thread number is:" + Thread.currentThread().getId());

        CompletableFuture.supplyAsync(() -> {
            System.out.println("my thread number is:" + Thread.currentThread().getId());
            return 1;
        }).thenApplyAsync(x -> {
            System.out.println("second thread number is:" + Thread.currentThread().getId());
            return x;
        }).thenApply(x -> {
            System.out.println("second thread number is:" + Thread.currentThread().getId());
            return x;
        });
    }

    static public class TxManager implements Runnable{
        static ThreadLocal<Integer> count = new ThreadLocal<>();
        static int count2 = 0;
        private int count3 = 0;

        public TxManager(int n) {
            count.set(n);
            count2 = n;
            count3 = n;

        }

        @Override
        public void run() {
            count.set(this.count3);
            count2 = this.count3;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("thread count %d, count2 %d, count3 %d", count.get(), count2, count3));
        }

    }

    @Test
    public void testThreadLocal() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i=0; i<10; i++) {
            executorService.submit(new TxManager(i));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

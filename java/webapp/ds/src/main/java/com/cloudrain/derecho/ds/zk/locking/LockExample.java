package com.cloudrain.derecho.ds.zk.locking;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lugan on 12/23/2016.
 */
public class LockExample {
    private final static int QTY = 5;
    private final static int REPETITIONS = QTY * 10;
    private final static String PATH = "/examples/lock";

    public static void main(String ...argv) throws Exception{
        FakeLimitedResource resource = new FakeLimitedResource();
        ExecutorService service = Executors.newFixedThreadPool(QTY);

        TestingServer server = new TestingServer();
        try {


        for (int i = 0; i < QTY; i++) {
            final int index = i;
            Callable<Void> task =  () -> {
                CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                try {
                    client.start();
                    ExampleClientThatLocks exampleClient = new ExampleClientThatLocks(client, PATH, resource, "client #" + index);
                    for (int j=0; j<REPETITIONS; j++) {
                        exampleClient.doWork(10, TimeUnit.SECONDS);
                    }
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    CloseableUtils.closeQuietly(client);
                }
                return null;
            };
            service.submit(task);
        }

        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);
        }finally {
            CloseableUtils.closeQuietly(server);
        }
    }
}

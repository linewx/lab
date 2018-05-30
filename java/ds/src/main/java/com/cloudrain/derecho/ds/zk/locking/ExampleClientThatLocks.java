package com.cloudrain.derecho.ds.zk.locking;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * Created by lugan on 12/23/2016.
 */
public class ExampleClientThatLocks {
    private InterProcessMutex lock;
    private String clientName;
    private FakeLimitedResource resource;

    public ExampleClientThatLocks(CuratorFramework client, String path, FakeLimitedResource resource, String clientName) {
        this.lock = new InterProcessMutex(client, path);
        this.clientName = clientName;
        this.resource = resource;

    }

    public void doWork(long time, TimeUnit timeUnit) throws Exception {
        if (!lock.acquire(time, timeUnit)) {
            throw new IllegalStateException(clientName + "could not acquire the lock");
        }
        try {
            System.out.println(clientName + " has the lock");
            resource.use();
        }finally {
            System.out.println(clientName + " releasing the lock");
            lock.release();

        }
    }
}

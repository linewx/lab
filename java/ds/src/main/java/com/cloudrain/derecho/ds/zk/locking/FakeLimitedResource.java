package com.cloudrain.derecho.ds.zk.locking;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lugan on 12/23/2016.
 */
public class FakeLimitedResource {
    private final AtomicBoolean inUse = new AtomicBoolean(false);

    public void use() throws InterruptedException {
        if (!inUse.compareAndSet(false, true)) {
            throw new InterruptedException("needs to be used by one client at a time");
        }

        try {
            Thread.sleep((long)( 3 * Math.random() * 1000));
        }finally {
            inUse.set(false);
        }
    }
}

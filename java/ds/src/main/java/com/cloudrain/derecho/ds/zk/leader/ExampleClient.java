package com.cloudrain.derecho.ds.zk.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lugan on 12/23/2016.
 */
public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable{

    private String name;
    private final LeaderSelector leaderSelector;
    //private final AtomicInteger leaderCount = new AtomicInteger();
    private int leaderCount = 0;

    public ExampleClient(CuratorFramework client, String path, String name) {
        this.name = name;
        this.leaderSelector = new LeaderSelector(client, path, this);
        this.leaderSelector.autoRequeue();
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        final int waitSeconds = (int) (5 * Math.random()) + 1;

        System.out.println(name + "is now the leader. Waiting " + waitSeconds + " seconds...");
        System.out.println(name + " has been leader " + leaderCount++ + "time(s) before.");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        }catch(InterruptedException e) {
            System.err.println(name + "was interrupted.");
            Thread.currentThread().interrupt();
        }finally {
            System.out.println(name + " relinquishing leadership.");
        }
    }

    public void start() throws IOException {
        leaderSelector.start();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }
}

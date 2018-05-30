package com.cloudrain.derecho.ds.zk.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Created by lugan on 12/26/2016.
 */
public class CrudExamples {
    public static void create(CuratorFramework client, String path, byte[]payload) throws Exception {
        client.create().forPath(path, payload);
    }

    public static void createEphemeral(CuratorFramework client, String path, byte[]payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    public static void createEphemeralSequential(CuratorFramework client, String path, byte[]pyaload) throws Exception {
        client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, pyaload);
    }

    public static void setData(CuratorFramework client, String path, byte[]payload) throws Exception {
        client.setData().forPath(path, payload);
    }

    public static void setDataAsync(CuratorFramework client, String path, byte[]payload) throws Exception {
        client.getCuratorListenable().addListener( (CuratorFramework myClient, CuratorEvent event) -> {
            //callback from set data
        });
        client.setData().inBackground().forPath(path, payload);
    }

    public static void setDataAsyncWithCallback(CuratorFramework client, BackgroundCallback callback, String path, byte[]payload) throws Exception {
        client.setData().inBackground(callback).forPath(path, payload);
    }

    public static void delete(CuratorFramework client, String path) throws Exception {
        client.delete().forPath(path);
    }

    public static void guranteedDelete(CuratorFramework client, String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    public static List<String> wathcedGetChildren(CuratorFramework client, String path, Watcher watcher) throws Exception {
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }

}

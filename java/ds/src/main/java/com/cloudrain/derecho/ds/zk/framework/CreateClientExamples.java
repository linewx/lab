package com.cloudrain.derecho.ds.zk.framework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by lugan on 12/26/2016.
 */
public class CreateClientExamples {
    public static CuratorFramework createSimple(String connectionsString) {
        return CuratorFrameworkFactory.newClient(connectionsString, new ExponentialBackoffRetry(1000, 3));
    }

    public static CuratorFramework createWithOptions(String connectionsString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder()
                .connectString(connectionsString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
    }
}

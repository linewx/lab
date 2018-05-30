package com.cloudrain.derecho.ds.zk.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugan on 12/23/2016.
 */
public class LeaderSelectorExample {
    public static final int CLIENT_SIZE = 10;
    public static final String PATH = "/example/leader";

    public static void main(String ...argv) throws Exception{
        System.out.println("Create " + CLIENT_SIZE + " clients, have each negotiate for leadership and then wait a random number of seconds before letting another leader election occur.");
        System.out.println("Notice that leader election is fair: all clients will become leader and will do so the same number of times.");

        TestingServer server = new TestingServer();
        List<ExampleClient> exampleClientList = new ArrayList<>();
        List<CuratorFramework> clientList = new ArrayList<>();
        try {
            for (int i=0; i<CLIENT_SIZE; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                clientList.add(client);
                ExampleClient exampleClient = new ExampleClient(client, PATH, "Client #" + i);
                exampleClientList.add(exampleClient);
                client.start();
                exampleClient.start();
            }
            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }finally {
            System.out.println("shutting down...");
            clientList.forEach(CloseableUtils::closeQuietly);
            exampleClientList.forEach(CloseableUtils::closeQuietly);
        }



    }
}

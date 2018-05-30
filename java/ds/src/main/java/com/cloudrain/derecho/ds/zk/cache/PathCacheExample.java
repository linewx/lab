package com.cloudrain.derecho.ds.zk.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by lugan on 12/23/2016.
 */
public class PathCacheExample {
    private static final String PATH = "/example/cache";

    public static void main(String... argv) throws Exception {
        TestingServer server = new TestingServer();
        CuratorFramework client = null;
        PathChildrenCache cache = null;
        try {
            client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            cache = new PathChildrenCache(client, PATH, true);
            cache.start();
            processCommand(client, cache);
        } finally {
            CloseableUtils.closeQuietly(cache);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(server);
        }
    }

    public static void addListener(PathChildrenCache cache) {
        PathChildrenCacheListener listener = (CuratorFramework client, PathChildrenCacheEvent event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("node added:" + ZKPaths.getNodeFromPath(event.getData().getPath()));
                    break;
                case CHILD_UPDATED:
                    System.out.println("node updated:" + ZKPaths.getNodeFromPath(event.getData().getPath()));
                    break;
                case CHILD_REMOVED:
                    System.out.println("node removed:" + ZKPaths.getNodeFromPath(event.getData().getPath()));
            }
        };
        cache.getListenable().addListener(listener);
    }

    public static void processCommand(CuratorFramework client, PathChildrenCache cache) throws Exception {
        printHelp();
        addListener(cache);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Boolean done = false;

        while (!done) {
            System.out.print("> ");
            String line = in.readLine();
            if (line == null) {
                break;
            }

            String command = line.trim();
            String[] parts = command.split("\\s");
            if (parts.length == 0) {
                continue;
            }

            String operation = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            if (operation.equalsIgnoreCase("help") || operation.equalsIgnoreCase("?")) {
                printHelp();
            } else if (operation.equalsIgnoreCase("q") || operation.equalsIgnoreCase("quit")) {
                done = true;
            } else if (operation.equals("set")) {
                updateNode(client, command, args);
            } else if (operation.equals("remove")) {
                removeNode(client, command, args);
            } else if (operation.equals("list")) {
                listNode(cache);
            }
            Thread.sleep(1000); // just to allow the console output to catch up

        }


    }

    private static void listNode(PathChildrenCache cache) {
        if (cache.getCurrentData().size() == 0) {
            System.out.println("empty *");
        } else {
            for (ChildData childData : cache.getCurrentData()) {
                System.out.println(childData.getPath() + " = " + new String(childData.getData()));
            }
        }
    }

    private static void removeNode(CuratorFramework client, String command, String[] args) {
        if (args.length != 1) {
            System.err.println("Syntax error(expected remove <path>):" + command);
            return;
        }

        String name = args[0];
        if (name.contains("/")) {
            System.err.println("Invalid node name" + name);
            return;
        }
        try {
            String path = ZKPaths.makePath(PATH, name);
            client.delete().forPath(path);
        } catch (Exception e) {
            //ignore
        }
    }

    private static void updateNode(CuratorFramework client, String command, String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Syntax error(expected set <path> <value):" + command);
            return;
        }

        String name = args[0];
        if (name.contains("/")) {
            System.err.println("Invalid node name " + name);
            return;
        }

        String path = ZKPaths.makePath(PATH, name);
        byte[] value = args[1].getBytes();
        try {
            client.setData().forPath(path, value);
        } catch (KeeperException.NoNodeException e) {
            client.create().creatingParentsIfNeeded().forPath(path, value);
        }
    }

    private static void printHelp() {
        System.out.println("An example of using PathChildrenCache. This example is driven by entering commands at the prompt:\n");
        System.out.println("set <name> <value>: Adds or updates a node with the given name");
        System.out.println("remove <name>: Deletes the node with the given name");
        System.out.println("list: List the nodes/values in the cache");
        System.out.println("quit: Quit the example");
        System.out.println();
    }


}

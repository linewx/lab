package com.cloudrain.derecho.ds.zk.discover;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.apache.zookeeper.KeeperException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lugan on 12/23/2016.
 */
public class DiscoveryExample {
    private static final String PATH = "/discovery/example";


    public static void main(String... argv) throws Exception {
        ServiceDiscovery<InstanceDetails> serviceDiscovery = null;
        Map<String, ServiceProvider<InstanceDetails>> providers = new HashMap<>();
        CuratorFramework client = null;
        TestingServer testingServer = new TestingServer();

        try {
            client = CuratorFrameworkFactory.newClient(testingServer.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            JsonInstanceSerializer<InstanceDetails> jsonInstanceSerializer = new JsonInstanceSerializer<>(InstanceDetails.class);
            serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                    .client(client)
                    .basePath(PATH)
                    .serializer(jsonInstanceSerializer)
                    .build();

            serviceDiscovery.start();

            processCommand(serviceDiscovery, providers, client);
        }finally {
            for (ServiceProvider<InstanceDetails> cache: providers.values()) {
                CloseableUtils.closeQuietly(cache);
            }
            CloseableUtils.closeQuietly(serviceDiscovery);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(testingServer);


        }
    }

    private static void processCommand(ServiceDiscovery<InstanceDetails> serviceDiscovery, Map<String, ServiceProvider<InstanceDetails>> providers, CuratorFramework client) throws Exception {
        printHelp();
        List<ExampleServer> servers = Lists.newArrayList();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            boolean done = false;
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
                String args[] = Arrays.copyOfRange(parts, 1, parts.length);

                if (operation.equalsIgnoreCase("help") || operation.equalsIgnoreCase("?")) {
                    printHelp();
                } else if (operation.equalsIgnoreCase("q") || operation.equalsIgnoreCase("quit")) {
                    done = true;
                } else if (operation.equals("add")) {
                    addInstance(args, client, command, servers);
                } else if (operation.equals("delete")) {
                    deleteInstance(args, command, servers);
                } else if (operation.equals("random")) {
                    listRandomInstance(args, serviceDiscovery, providers, command);
                } else if (operation.equals("list")) {
                    listInstances(serviceDiscovery);
                }
            }
        } finally {
            for (ExampleServer server : servers) {
                CloseableUtils.closeQuietly(server);
            }
        }
    }

    private static void listRandomInstance(String[] args, ServiceDiscovery<InstanceDetails> serviceDiscovery, Map<String, ServiceProvider<InstanceDetails>> providers, String command) throws Exception {
        if (args.length != 1) {
            System.out.println("syntax error(expected random <name>):" + command);
            return;
        }

        String serviceName = args[0];

        ServiceProvider<InstanceDetails> serviceProvider = providers.get(serviceName);

        if (serviceProvider == null) {
            serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName(serviceName).providerStrategy(new RandomStrategy<>()).build();
            providers.put(serviceName, serviceProvider);
            serviceProvider.start();
            Thread.sleep(2500); // give the provider to warn up
        }

        ServiceInstance serviceInstance = serviceProvider.getInstance();

        if (serviceInstance == null) {
            System.err.println("No instance named: " + serviceName);
        } else {
            outputInstance(serviceInstance);
        }
    }

    private static void listInstances(ServiceDiscovery<InstanceDetails> serviceDiscovery) throws Exception {
        try {
            Collection<String> serviceNames = serviceDiscovery.queryForNames();
            for (String oneServiceName : serviceNames) {
                Collection<ServiceInstance<InstanceDetails>> serviceInstances = serviceDiscovery.queryForInstances(oneServiceName);
                for (ServiceInstance<InstanceDetails> oneServiceInstance : serviceInstances) {
                    outputInstance(oneServiceInstance);
                }
            }
        }catch (KeeperException.NoNodeException e) {
            //ignore
        } finally{
            //CloseableUtils.closeQuietly(serviceDiscovery);
        }

    }

    private static void deleteInstance(String[] args, String command, List<ExampleServer> servers) {
        if (args.length != 1) {
            System.err.println("syntax error(expected delete <name>):" + command);
            return;
        }

        String serviceName = args[0];

        List<ExampleServer> filteredServers = servers.stream().filter(oneServer ->
                oneServer.getServiceInstance().getName().endsWith(serviceName)
        ).collect(Collectors.toList());

        if (filteredServers == null || filteredServers.isEmpty()) {
            System.err.println("no servers found named: " + serviceName);
            return;
        }


        servers.remove(filteredServers.get(0));
        CloseableUtils.closeQuietly(filteredServers.get(0));
        System.out.println("removed a random instance of: " + serviceName);
    }

    private static void addInstance(String[] args, CuratorFramework client, String command, List<ExampleServer> servers) throws Exception {
        if (args.length < 2) {
            System.err.println("syntax error (expected add <name> <description>):" + command);
        }

        StringBuilder description = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            if (i > 1) {
                description.append(" ");
            }

            description.append(args[i]);
        }

        String serviceName = args[0];
        ExampleServer exampleServer = new ExampleServer(client, PATH, serviceName, description.toString());

        servers.add(exampleServer);
        exampleServer.start();

        System.out.println(serviceName + " added");
    }


    private static void outputInstance(ServiceInstance<InstanceDetails> serviceInstance) {
        System.out.println("\t" + serviceInstance.getPayload().getDescription() + ": " + serviceInstance.buildUriSpec());
    }

    private static void printHelp() {
        System.out.println("An example of using the ServiceDiscovery APIs. This example is driven by entering commands at the prompt:\n");
        System.out.println("add <name> <description>: Adds a mock service with the given name and description");
        System.out.println("delete <name>: Deletes one of the mock services with the given name");
        System.out.println("list: Lists all the currently registered services");
        System.out.println("random <name>: Lists a random instance of the service with the given name");
        System.out.println("quit: Quit the example");
        System.out.println();
    }
}

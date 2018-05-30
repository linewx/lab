package com.cloudrain.derecho.ds.zk.discover;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.security.Provider;

/**
 * Created by lugan on 12/23/2016.
 */
public class ExampleServer implements Closeable{
    private final ServiceDiscovery<InstanceDetails> serviceDiscovery;
    private final ServiceInstance<InstanceDetails> serviceInstance;

    public ExampleServer(CuratorFramework client, String path, String serviceName, String description) throws Exception{
        UriSpec uriSpec = new UriSpec("{scheme}://foo.com:{port}");
        serviceInstance = ServiceInstance.<InstanceDetails>builder()
                .name(serviceName)
                .payload(new InstanceDetails(description))
                .port((int)(65535 * Math.random()))
                .uriSpec(uriSpec)
                .build();
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);

        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .basePath(path)
                .serializer(serializer)
                .thisInstance(serviceInstance)
                .build();
    }

    public ServiceInstance<InstanceDetails> getServiceInstance() {
        return serviceInstance;
    }

    public void start() throws Exception{
        serviceDiscovery.start();
    }

    @Override
    public void close() throws IOException {
        serviceDiscovery.close();
    }
}

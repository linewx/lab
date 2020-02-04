package com.zz.lab.springioc.service;

import lombok.Data;

@Data
public class ServiceProvider {
    private ServiceListener serviceListener;

    public ServiceProvider(ServiceListener serviceListener) {
        this.serviceListener = serviceListener;
    }
}

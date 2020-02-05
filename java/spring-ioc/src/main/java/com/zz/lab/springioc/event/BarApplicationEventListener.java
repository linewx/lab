package com.zz.lab.springioc.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BarApplicationEventListener implements ApplicationListener<BarApplicationEvent> {
    @Override
    public void onApplicationEvent(BarApplicationEvent event) {
        System.out.println("get bar application event");
    }
}

package com.zz.lab.springioc.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BarApplicationEvent extends ApplicationEvent {
    public BarApplicationEvent(Object source) {
        super(source);
    }
}

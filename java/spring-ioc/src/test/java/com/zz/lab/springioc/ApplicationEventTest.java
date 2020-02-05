package com.zz.lab.springioc;

import com.zz.lab.springioc.event.BarApplicationEvent;
import com.zz.lab.springioc.event.BarApplicationEventListener;
import com.zz.lab.springioc.event.FooApplicationEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//测试IOC的注入
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FooApplicationEventListener.class, BarApplicationEventListener.class})
public class ApplicationEventTest {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void testEvent() {
        //the message foo and bar should appear in the log
        BarApplicationEvent barApplicationEvent = new BarApplicationEvent("start up");
        applicationEventPublisher.publishEvent(barApplicationEvent);

    }
}

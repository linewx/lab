package com.zz.lab.springioc;

import com.zz.lab.springioc.config.FooAdvisorConfig;
import com.zz.lab.springioc.entity.Foo;
import com.zz.lab.springioc.event.BarApplicationEvent;
import com.zz.lab.springioc.event.BarApplicationEventListener;
import com.zz.lab.springioc.event.FooApplicationEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FooAdvisorConfig.class)
public class AopTest {
    @Autowired
    private Foo foo;

    @Autowired
    private PointcutAdvisor pointcutAdvisor;

    @Test
    public void testAop() {
        Foo foo = Foo.builder().name("xm").type("male").build();

        //weaver
        ProxyFactory proxyFactory = new ProxyFactory(foo);
        //advisor include pointcut and advice
        proxyFactory.addAdvisor(pointcutAdvisor);

        Foo fooProxy = (Foo)proxyFactory.getProxy();
        System.out.println(fooProxy.getFullName());
    }

    @Test
    public void testSprintAop() {
        System.out.println(foo.getFullName());
    }

}

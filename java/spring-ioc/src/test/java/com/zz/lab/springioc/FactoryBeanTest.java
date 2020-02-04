package com.zz.lab.springioc;

import com.zz.lab.springioc.config.BarFactoryBeanConfig;
import com.zz.lab.springioc.config.FooFactoryBeanConfig;
import com.zz.lab.springioc.entity.Bar;
import com.zz.lab.springioc.entity.Foo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//测试IOC的注入
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FooFactoryBeanConfig.class,BarFactoryBeanConfig.class})
public class FactoryBeanTest {

    @Autowired
    private Foo foo;

    @Autowired
    private Foo foo2;

    @Autowired
    private Bar bar;

    @Autowired
    private Bar bar2;



    @Test
    public void testFactoryBean() {
        Assert.assertSame(foo, foo2);
        Assert.assertNotSame(bar, bar2);

    }



}

package com.zz.lab.springioc;

import com.zz.lab.springioc.entity.Foo;
import com.zz.lab.springioc.event.BarApplicationEventListener;
import com.zz.lab.springioc.event.FooApplicationEventListener;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FooApplicationEventListener.class, BarApplicationEventListener.class})

public class BeanWrapperTest {
    //wrapper java refectionAPI  in beanwrapper.
    @Test
    public void testBeanWrapper() {
        Foo foo = new Foo();
        BeanWrapper beanWrapper = new BeanWrapperImpl(foo);
        Map<String, String> properties = new HashMap<>();
        properties.put("name", "foo");
        beanWrapper.setPropertyValues(properties);
        Assert.assertEquals("foo", foo.getName());
    }
}

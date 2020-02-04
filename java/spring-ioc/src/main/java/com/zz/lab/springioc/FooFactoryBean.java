package com.zz.lab.springioc;

import com.zz.lab.springioc.entity.Foo;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class FooFactoryBean extends AbstractFactoryBean<Foo> {
    @Override
    public Class<?> getObjectType() {
        return Foo.class;
    }

    @Override
    protected Foo createInstance() throws Exception {
        return Foo.builder().name("haha").type("test").build();
    }
}

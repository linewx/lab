package com.zz.lab.springioc;

import com.zz.lab.springioc.entity.Bar;
import com.zz.lab.springioc.entity.Foo;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class BarFactoryBean extends AbstractFactoryBean<Bar> {
    @Override
    public Class<?> getObjectType() {
        return Bar.class;
    }

    @Override
    protected Bar createInstance() throws Exception {
        return Bar.builder().name("haha").type("test").build();
    }


}

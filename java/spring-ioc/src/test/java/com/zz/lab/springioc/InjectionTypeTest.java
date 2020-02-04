package com.zz.lab.springioc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//测试IOC的注入
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyRestTemplateConfiguration.class)
public class InjectionTypeTest {
    @Autowired
    @MyLoadBalanced
    private MyRestTemplate template1; // qualifier > name > type

    @Autowired
    private MyRestTemplate template2; // by name

    @Autowired
    private MyRestTemplate template3;

    // wrong, because of ambiguous meaning
//    @Autowired
//    private MyRestTemplate template;

    @Autowired
    private List<MyRestTemplate> templateList;

    @Autowired
    @MyLoadBalanced
    private List<MyRestTemplate> lbMyRestTemplate;

    @Before
    public void setup() {
        for (MyRestTemplate restTemplate : templateList) {
            restTemplate.setName("hello");
        }
    }

    @Test
    public void testMyRestTemplate() {
        Assert.assertEquals(lbMyRestTemplate.size(), 1);
        Assert.assertEquals(templateList.size(), 3);

        templateList.forEach(x -> {
            x.setName("normal");
        });

        lbMyRestTemplate.forEach(x -> {
            x.setName("lb");
        });

       Assert.assertEquals(template1.getName(), "lb");
       Assert.assertEquals(template2.getName(), "lb");
       Assert.assertEquals(template3.getName(), "normal");

    }
}

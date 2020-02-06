package com.zz.lab.springioc;

import com.zz.lab.springioc.event.BarApplicationEventListener;
import com.zz.lab.springioc.event.FooApplicationEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FooApplicationEventListener.class, BarApplicationEventListener.class})
public class TransactionTest {
    @Test
    public void testTransactionTemplate() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.execute(x -> null);
    }
}

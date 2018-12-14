package com.zz.basic;

import org.junit.Assert;
import org.junit.Test;

public class BasicTest {
    @Test
    public  void testStringFormat() {
        String result = String.format("%s %s %s", "a", "b", 1123);
        Assert.assertEquals(result, "a b 1123");
    }
}

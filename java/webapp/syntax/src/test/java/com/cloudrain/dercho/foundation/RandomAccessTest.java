package com.cloudrain.dercho.foundation;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

/**
 * Created by lugan on 5/18/2016.
 */
public class RandomAccessTest {
    @Test
    public void randomAccessTest() {
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        Assert.assertTrue(arrayList instanceof RandomAccess);
        Assert.assertFalse(linkedList instanceof RandomAccess);
    }
}

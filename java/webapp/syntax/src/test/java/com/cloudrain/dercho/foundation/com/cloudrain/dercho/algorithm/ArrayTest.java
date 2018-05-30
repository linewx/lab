package com.cloudrain.dercho.foundation.com.cloudrain.dercho.algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lugan on 5/17/2016.
 */
public class ArrayTest {

    @Test
    public void ArrayClassTest() {
        int[] iArray = new int[10];
        Assert.assertEquals(iArray.getClass().getName(), "[I");

        Byte[] bArray = new Byte[10];
        Assert.assertEquals(bArray.getClass().getName(), "[Ljava.lang.Byte;");
    }

    @Test
    public void lengthTest() {
        int [] iArray = new int[10];
        Assert.assertEquals(iArray.length, 10);

        int [] bArray = {1,2,3,4,5};
        Assert.assertEquals(bArray.length, 5);

        Integer[] IArray = {1,2,null,null, null};
        Assert.assertEquals(IArray.length, 5);
        Assert.assertEquals(IArray[1], Integer.valueOf(2));
        Assert.assertNull(IArray[5]);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionTest() {
        int [] iArray = new int[0];
        int i = iArray[1];
    }

    @Test
    public void dimensionsTest() {
        int [][] dimensions = {
                {0, 1},
                {2, 3}
        };

        Assert.assertEquals(dimensions[0][0], 0);
        Assert.assertEquals(dimensions[1][0], 2);
        Assert.assertEquals(dimensions[0][1], 1);
        Assert.assertEquals(dimensions[1][1], 3);
    }




}

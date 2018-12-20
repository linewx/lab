package com.zz.playground.algo.binarysearch;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTest {
    @Test
    public void testBinarySearch() {
        int []numbers = {1, 2, 5, 6, 7};
        Assert.assertEquals(BinarySearch.bsearch(numbers, 2), 1);

        Assert.assertEquals(BinarySearch.bsearch(numbers, 3), -1);

    }

    //find the last element index
    @Test
    public void testBinarySearch2() {
        int []numbers = {1, 2, 2, 2, 5, 5 , 6, 6, 6,6,6, 7};
        Assert.assertEquals(BinarySearch.bsearch2(numbers, 2), 1);
        Assert.assertEquals(BinarySearch.bsearch2(numbers, 5), 4);
        Assert.assertEquals(BinarySearch.bsearch2(numbers, 6), 6);

        Assert.assertEquals(BinarySearch.bsearch2(numbers, 3), -1);
    }

    //find the last element index
    @Test
    public void testBinarySearch3() {
        int []numbers = {1, 2, 2, 2, 5, 5 , 6, 6, 6,6,6, 7};
        Assert.assertEquals(BinarySearch.bsearch3(numbers, 2), 3);
        Assert.assertEquals(BinarySearch.bsearch3(numbers, 5), 5);
        Assert.assertEquals(BinarySearch.bsearch3(numbers, 6), 10);

        Assert.assertEquals(BinarySearch.bsearch3(numbers, 3), -1);
    }

    //find the first element equal or more than value
    @Test
    public void testBinarySearch4() {
        int []numbers = {1, 2, 2, 2, 5, 5 , 6, 6, 6,6,6, 7};
        Assert.assertEquals(BinarySearch.bsearch4(numbers, 4), 4);
        Assert.assertEquals(BinarySearch.bsearch4(numbers, 2), 1);
        Assert.assertEquals(BinarySearch.bsearch4(numbers, 8), -1);

        Assert.assertEquals(BinarySearch.bsearch4(numbers, 0), 0);
    }

    //find the last element equal or less than value
    @Test
    public void testBinarySearch5() {
        int []numbers = {1, 2, 2, 2, 5, 5 , 6, 6, 6,6,6, 7};
        Assert.assertEquals(BinarySearch.bsearch5(numbers, 4), 3);
        Assert.assertEquals(BinarySearch.bsearch5(numbers, 2), 3);
        Assert.assertEquals(BinarySearch.bsearch5(numbers, 8), 11);

        Assert.assertEquals(BinarySearch.bsearch5(numbers, 0), -1);
    }

    //find the first element greater than value
    @Test
    public void testBinarySearch6() {
        int []numbers = {1, 2, 2, 2, 5, 5 , 6, 6, 6,6,6, 7};
        Assert.assertEquals(BinarySearch.bsearch6(numbers, 4), 4);
        Assert.assertEquals(BinarySearch.bsearch6(numbers, 2), 4);
        Assert.assertEquals(BinarySearch.bsearch6(numbers, 1), 1);
        Assert.assertEquals(BinarySearch.bsearch6(numbers, 8), -1);

        Assert.assertEquals(BinarySearch.bsearch6(numbers, 0), 0);
    }

    //find the last element less than value
    @Test
    public void testBinarySearch7() {
        int []numbers = {1, 2, 2, 2, 5, 5 , 6, 6, 6,6,6, 7};
        Assert.assertEquals(BinarySearch.bsearch7(numbers, 4), 3);
        Assert.assertEquals(BinarySearch.bsearch7(numbers, 2), 0);
        Assert.assertEquals(BinarySearch.bsearch7(numbers, 6), 5);
        Assert.assertEquals(BinarySearch.bsearch7(numbers, 8), 11);

        Assert.assertEquals(BinarySearch.bsearch7(numbers, 0), -1);
    }

}

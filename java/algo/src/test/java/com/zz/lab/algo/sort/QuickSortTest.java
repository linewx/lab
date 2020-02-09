package com.zz.lab.algo.sort;

import org.junit.Test;

import java.util.Arrays;

public class QuickSortTest {
    @Test
    public void testSort() {
        int []numbers = new int[]{5,4,6, 9, 8,2,1};
        quickSort(numbers, 0, numbers.length -1);
        System.out.println(Arrays.toString(numbers));
    }

    public void quickSort(int [] data, int start, int end) {
        //end condition
        if (start >= end) {
            return;
        }

        //recursive

        //partition
        int position = doPartition(data, start, end);

        //do sub sort
        quickSort(data, start, position -1);
        quickSort(data, position+ 1, end);

    }


    public int doPartition(int []data, int start, int end) {
        //use first position as the partition
        int index = start;
        int low = index;
        int partitionNumber = data[end];

        for (;index < end; index ++) {
            if (data[index] < partitionNumber) {
                //swap index and low position, and increate low
                int tmp = data[low];
                data[low] = data[index];
                data[index] = tmp;
                low++;
            }
            //else do nothing
        }

        //swap the partition
        data[end] = data[low];
        data[low] = partitionNumber;
        return low;
    }
}

package com.zz.lab.algo.sort;

import org.junit.Test;

import java.util.Arrays;

public class TopKTest {
    @Test
    public void testSort() {
        int []numbers = new int[]{5,4,6, 9, 8,2,1};
        int k = 5;
        int result = topK(numbers, 0, numbers.length -1, k - 1);
        System.out.println(result);
    }

    public int topK(int [] data, int start, int end, int k) {
        //recursive

        //partition
        int position = doPartition(data, start, end);

        if (position == k) {
            return data[position];
        }

        if (position > k)  {
            return topK(data, start, position-1, k);
        }else {
            return topK(data, position+ 1, end, k);
        }
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

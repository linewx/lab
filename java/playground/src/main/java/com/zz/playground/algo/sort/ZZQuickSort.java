package com.zz.playground.algo.sort;

public class ZZQuickSort{

    //原地排序
    public void quicksort(int []numbers, int start, int end) {
        if (start < end) {

            int pivotIndex = partition(numbers, start, end);
            quicksort(numbers, start, pivotIndex -1 );
            quicksort(numbers, pivotIndex + 1, end);
        }
    }

    private int partition(int []numbers, int start, int end) {
        int pivot = numbers[end];
        int i = start;
        int j = start;
        for ( ; j < end; j++) {
            //循环不变式是在小于j的所有元素中，i下标之后的都是小于pivot的
            //循环也是一种递归， 递归也是循环
            /***
             *
             */
            int theValue = numbers[j];
            if (theValue < pivot) {
                //swap i j
                swap(numbers, i, j);
                i++;
            }
        }

        swap(numbers, i ,end);
        return i;
    }

    private void swap(int []numbers, int i, int j) {
        int k = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = k;
    }

    public static void main(String ...argv) {
        ZZQuickSort zzSort = new ZZQuickSort();
        int []numbers = {1,45, 5, 234, 2, 3, 5 ,2};
        zzSort.quicksort(numbers, 0, numbers.length -1);
        for (int i : numbers) {
            System.out.println(i);
        }

    }


}

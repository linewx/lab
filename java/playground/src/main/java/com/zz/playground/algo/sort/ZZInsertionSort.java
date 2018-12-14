package com.zz.playground.algo.sort;

public class ZZInsertionSort {

    //原地排序
    public void sort(int []numbers) {
        //
        for (int i=0; i<numbers.length; i++) {
            insert(numbers, i);
        }
    }

    private void insert(int []numbers, int index) {
        if (index == 0) {
            return;
        }

        int theValue = numbers[index];
        int i= index;
        for (; i >= 0; i--) {
            if (numbers[i-1] > theValue) {
                numbers[i] = numbers[i-1];

            }else {
                break;
            }

        }
        numbers[i] = theValue;
    }

    private void swap(int []numbers, int i, int j) {
        int k = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = k;
    }

    public static void main(String ...argv) {
        ZZInsertionSort zzSort = new ZZInsertionSort();
        int []numbers = {1,6,7,8,5};
        zzSort.sort(numbers);
        for (int i : numbers) {
            System.out.println(i);
        }

    }


}

package com.zz.playground.algo.sort;

public class ZZBubbleSort {
    //原地排序
    public void sort(int []numbers) {
        //
        for (int i=0; i<numbers.length; i++) {
            for (int j=i; j <numbers.length; j++) {
                if (numbers[j] < numbers[i]) {
                    swap(numbers, i, j);
                }
            }
        }
    }

    private void swap(int []numbers, int i, int j) {
        int k = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = k;
    }

    public static void main(String ...argv) {
        ZZBubbleSort zzSort = new ZZBubbleSort();
        int []numbers = {1,4, 5, 3, 2};
        zzSort.sort(numbers);
        for (int i : numbers) {
            System.out.println(i);
        }
    }


}

package com.zz.playground.algo.binarysearch;

public class BinarySearch {
    public static int bsearch(int[] numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化
            int midValue = numbers[mid];

            if (value < midValue) {
                high = mid -1;
            }else if (value > midValue) {
                low = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    //the first element match the value
    public static int bsearch2(int []numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化
            int midValue = numbers[mid];

            if (value < midValue) {
                high = mid -1;
            }else if (value > midValue) {
                low = mid + 1;
            }else {
                if (mid == 0 || numbers[mid -1] != value) {
                    return mid;
                }else {
                    high = mid  - 1;
                }
            }
        }

        return -1;
    }

    // the last element to match the value
    public static int bsearch3(int []numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化
            int midValue = numbers[mid];

            if (value < midValue) {
                high = mid -1;
            }else if (value > midValue) {
                low = mid + 1;
            }else {
                if (mid == (numbers.length - 1) || numbers[mid  + 1] != value) {
                    return mid;
                }else {
                    low = mid + 1;
                }
            }
        }

        return -1;
    }

    //first element equal or more than the value
    public static int bsearch4(int []numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化

            int midValue = numbers[mid];

            if (value <= midValue) {
                if (mid == 0 || numbers[mid-1] < value) {
                    return mid;
                }else {
                    high = mid -1;
                }
            }else {
                low = mid + 1;
            }
        }

        return -1;
    }

    //last element equal or less than the value
    public static int bsearch5(int[] numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化

            int midValue = numbers[mid];

            if (midValue <= value) {
                if (mid == (numbers.length  - 1) || numbers[mid+1] > value) {
                    return mid;
                }else {
                    low = mid  + 1;
                }
            }else {
                high = mid - 1;
            }
        }

        return -1;
    }

    //find the first element greater than the value
    public static int bsearch6(int[] numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化

            int midValue = numbers[mid];

            if (midValue <= value) {
                low = mid  + 1;
            }else {
                if (mid == 0 || numbers[mid - 1] <= value) {
                    return mid;
                }else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    //find the last element less than the value
    public static int bsearch7(int[] numbers, int value) {
        if (numbers.length == 0) {
            return -1;
        }

        int low = 0;
        int high = numbers.length -1;
        while (low <= high) {
            int mid = (low + high)/2; //可以优化

            int midValue = numbers[mid];

            if (midValue < value) {
                if (mid == (numbers.length - 1) || numbers[mid + 1] >=value) {
                    return mid;
                }else {
                    low = mid  + 1;
                }
            }else {
                high = mid - 1;
            }
        }

        return -1;
    }
}

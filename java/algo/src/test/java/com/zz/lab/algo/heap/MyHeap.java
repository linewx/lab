package com.zz.lab.algo.heap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyHeap implements Queue<Integer> {
    private int[] data;
    private int size;

    public MyHeap() {
        data = new int[100];
        size = 0;
    }


    @Override
    public boolean add(Integer integer) {
        if (size == 0) {
            data[size] = integer;
            size++;
        } else {
            //
            data[size] = integer;
            size++;
            int index = size - 1;
            while (index > 0) {
                int currentNodeValue = data[index];
                int parentNodeValue = data[(index - 1) / 2];
                if (currentNodeValue < parentNodeValue) {
                    //swap
                    int tmp = parentNodeValue;
                    data[index] = parentNodeValue;
                    data[(index - 1) / 2] = currentNodeValue;
                    index = (index - 1) / 2;
                } else {
                    break;
                }
            }
        }

        return true;
    }



    @Override
    public boolean offer(Integer integer) {
        return false;
    }

    @Override
    public Integer remove() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int result = data[0];
        //replace the first element with last element
        data[0] = data[size -1];
        size --;

        int index = 0;

        while(index < size) {
            int left = index * 2 + 1;
            int right = index * 2 + 1;
            if (left >= size) {
                //no node, no swap needed
                break;
            }
            boolean useLeft = false;
            if (right > size) {
                useLeft = true;
            }else {
                useLeft = left < right;
            }

            boolean stop = true;
            if (useLeft) {
                if (data[left] < data[index]) {
                        //swap left and parent
                    int tmp = data[index];
                    data[index] = data[left];
                    data[left] = tmp;
                    //increase index
                    index = index * 2 + 1;
                    stop = false;
                }
            }else {
                if (data[right] < data[index]) {
                    int tmp = data[index];
                    data[index] = data[right];
                    data[right] = tmp;
                    //increase index
                    index = index * 2 + 2;
                    stop = false;
                }
            }

            if (stop) {
                break;
            }


        }
        return result;
    }

    @Override
    public Integer poll() {
        return null;
    }

    @Override
    public Integer element() {
        return null;
    }

    @Override
    public Integer peek() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}

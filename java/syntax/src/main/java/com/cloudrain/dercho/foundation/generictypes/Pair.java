package com.cloudrain.dercho.foundation.generictypes;

/**
 * Created by lugan on 4/28/2016.
 */
public class Pair<T> {
    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public static void main(String ...argv) {
        Pair<String> pair = new Pair<String>("first", "second");
        String first = pair.getFirst();
        String second = pair.getSecond();
        System.out.println(first);
        System.out.println(second);
    }
}

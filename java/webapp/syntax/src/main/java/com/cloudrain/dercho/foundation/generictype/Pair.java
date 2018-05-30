package com.cloudrain.dercho.foundation.generictype;

/**
 * Created by luganlin on 4/30/16.
 */
public class Pair<T> {
    private T first;
    private T second;

    /**
     * <? super Manager>
     * @param a
     */
    void setFirst(T a) {
        first = a;
    }

    void setSecond(T b) {
        second = b;
        Object a = new Object();

    }
}

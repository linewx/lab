package com.cloudrain.dercho.foundation.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by lugan on 5/4/2016.
 */
public class IterSample<E> implements Iterable<E>{
    private ArrayList<E> data;

    public IterSample(ArrayList<E> data) {
        this.data = data;
    }

    public Iterator<E> iterator() {
        return data.iterator();
    }

    public static void main(String ...argv) {
        IterSample<String> iter = new IterSample<String>(new ArrayList<String>(Arrays.asList("hello", "world")));
        for(String one : iter) {
            System.out.println(one);
        }

        Iterator<String> oneIter = iter.iterator();
        while(oneIter.hasNext()) {
            String a = oneIter.next();
            System.out.println(a);
        }

        Iterator<String> twoIter = iter.iterator();
        twoIter.forEachRemaining(System.out::println);

        Iterator<String> threeIter = iter.iterator();
        threeIter.forEachRemaining(element -> System.out.println(element));
    }



}

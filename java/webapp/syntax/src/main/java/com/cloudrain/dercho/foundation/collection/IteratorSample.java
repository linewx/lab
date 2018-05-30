package com.cloudrain.dercho.foundation.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by luganlin on 5/5/16.
 */
public class IteratorSample {

    public static void main(String ...argv) {
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList("this", "is", "my", "list"));
        Iterator<String> myIterator = myList.iterator();
        while(myIterator.hasNext()) {
            System.out.println(myIterator.next());
        }

        Iterator<String> removeIterator = myList.iterator();
        while(removeIterator.hasNext()) {
            String item = removeIterator.next();
            if (item.equals("my")) {
                removeIterator.remove();
            }
        }

        Iterator<String> foreachIterator = myList.iterator();
        foreachIterator.forEachRemaining(System.out::println);

        String[] a = {"hello", "world", null, "!"};
        System.out.println(a.length);

    }
}

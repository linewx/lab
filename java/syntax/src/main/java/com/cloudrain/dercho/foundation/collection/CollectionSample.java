package com.cloudrain.dercho.foundation.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by luganlin on 5/5/16.
 */
public class CollectionSample {

    public static void main(String ...argv) {
        Collection<String> myCollection = new ArrayList<>(Arrays.asList("this", "is", "my", "collection", "example"));
        Collection<String> myOriginCollection = new ArrayList<>(Arrays.asList("this", "is", "my", "collection", "example"));
        Collection<String> emptyCollection = new ArrayList<>();

        //size method
        System.out.println(myCollection.size());


        //isEmpty
        System.out.println(myCollection.isEmpty());
        System.out.println(emptyCollection.isEmpty());



        //contains
        System.out.println(myCollection.contains("this"));
        System.out.println(myCollection.contains("your"));

        //containsAll
        Collection<String> mySubCollection = new ArrayList<>(Arrays.asList("this", "my", "collection", "example"));
        System.out.println(myCollection.containsAll(mySubCollection));

        //remove
        myCollection.remove("this");
        System.out.println(myCollection.size());

        //remove all
        myCollection.removeAll(mySubCollection);
        System.out.println(myCollection.size());

        //clear
        myCollection.clear();
        System.out.println(myCollection.size());


        myOriginCollection.forEach(System.out::print);
        System.out.println("------");
        Collection<String> retainCollection = new ArrayList<>(Arrays.asList("this", "your"));
        Collection<String> otherRetainCollection = new ArrayList<>(Arrays.asList("that"));
        System.out.println(myOriginCollection.retainAll(retainCollection));
        System.out.println(myOriginCollection.retainAll(Arrays.asList("this")));
        System.out.println(myOriginCollection.retainAll(otherRetainCollection));
        System.out.println("------");
        myOriginCollection.forEach(System.out::print);


        Object[] collectionArray = Arrays.asList("hello", "world").toArray();
        for(int i=0; i<collectionArray.length; i++) {
            System.out.println(collectionArray[i]);
        }
        String[] test = {"sd", "sdf", "cbcbcv", "sdfsdf"};
        System.out.println("######");
        String[] strCollectionArray = Arrays.asList("hello", "world").toArray(test);
        for(int i=0; i<strCollectionArray.length; i++) {
            System.out.println(strCollectionArray[i]);
        }

    }
}

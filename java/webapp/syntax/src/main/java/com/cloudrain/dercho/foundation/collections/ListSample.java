package com.cloudrain.dercho.foundation.collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by lugan on 5/9/2016.
 */
public class ListSample {
    public static void main(String ...argv) {
        ArrayList<String> myList = new ArrayList<>(Arrays.asList("hello", "world"));
        ArrayList<String> toSortList = new ArrayList<>(Arrays.asList("2", "1", "3"));

        ArrayList<String> dupList = new ArrayList<>(Arrays.asList("1", "1", "1"));
        //replaceAll method
        ArrayList<String> myListToReplaced = new ArrayList<>(Arrays.asList("hello", "world"));
        myListToReplaced.replaceAll(t-> t + "!");
        myListToReplaced.forEach(System.out::println);

        //addAll method
        myList.addAll(Arrays.asList("welcome", "to","the", "world"));
        myList.forEach(System.out::println);

        //sort method
        toSortList.sort(String::compareTo);
        toSortList.forEach(System.out::println);

        //get method
        try {
            System.out.println(myList.get(10));
        }catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(myList.get(0));

        //set method
        try {
            System.out.println(myList.set(10, "good"));
        }catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(myList.set(5, "good"));
        }catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        myList.forEach(System.out::println);

        //add
        myList.add(5, "!");
        myList.forEach(System.out::println);

        //remove method
        myList.remove(6);
        myList.forEach(System.out::println);

        //indexof
        System.out.println(dupList.indexOf("1"));
        System.out.println(dupList.lastIndexOf("1"));

        //ListIteractor
        ListIterator<String> iter = myList.listIterator();
        System.out.println(iter.next());
        iter.set("hey");
        myList.forEach(System.out::print);

        iter.add("hello");
        myList.forEach(System.out::print);

        //subList
        List<String> subList = myList.subList(0,1);
        subList.forEach(System.out::println);
        subList.set(0, "Heyhey!!!");
        System.out.println("#############");
        myList.forEach(System.out::print);










    }
}

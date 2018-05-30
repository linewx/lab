package com.cloudrain.dercho.foundation.generictypes;

/**
 * Created by lugan on 4/28/2016.
 */
public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public static void main(String ...argv) {
        /*MyNode mn = new MyNode(5);
        Node n = mn;            // A raw type - compiler throws an unchecked warning
        n.setData("Hello");
        Integer x = mn.data;    // Causes a ClassCastException to be thrown.*/

        MyNode mn = new MyNode(5);
        Node n = (MyNode)mn;         // A raw type - compiler throws an unchecked warning
        n.setData("Hello");
        //Integer x = (String)mn.data; // Causes a ClassCastException to be thrown.
    }
}

package com.cloudrain.dercho.foundation.generictype;

/**
 * Created by luganlin on 4/29/16.
 */
public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public static void main(String ...argv) {
        MyNode mn = new MyNode(5);
        Node n = mn;            // A raw type - compiler throws an unchecked warning
        n.setData("Hello");
        //Integer x = mn.data;    // Causes a ClassCastException to be thrown.


    }
}
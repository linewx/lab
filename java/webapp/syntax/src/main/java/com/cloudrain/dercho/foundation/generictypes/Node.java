package com.cloudrain.dercho.foundation.generictypes;

/**
 * Created by lugan on 4/28/2016.
 */
public class Node<T> {
    public T data;

    public Node(T data) {
        this.data = data;
    }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

package com.cloudrain.dercho.foundation.generictype;

/**
 * Created by luganlin on 4/29/16.
 */
public class Node<T> {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

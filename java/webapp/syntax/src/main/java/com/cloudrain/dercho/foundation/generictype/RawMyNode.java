package com.cloudrain.dercho.foundation.generictype;

/**
 * Created by luganlin on 4/29/16.
 */
public class RawMyNode extends RawNode {
    public RawMyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public void setData(Object data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public static void main(String ...argv) {
        RawMyNode mn = new RawMyNode(5);
        RawNode n = mn;            // A raw type - compiler throws an unchecked warning
        n.setData(Integer.valueOf(3));
        //Integer x = (Integer) mn.data;    // Causes a ClassCastException to be thrown.
    }
}
package com.cloudrain.dercho.foundation.generictype;

/**
 * Created by luganlin on 4/29/16.
 */
public class RawNode {

    public Object data;

    public RawNode(Object data) { this.data = data; }

    public void setData(Object data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

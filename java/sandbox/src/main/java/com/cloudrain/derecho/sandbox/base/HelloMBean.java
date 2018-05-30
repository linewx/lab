package com.cloudrain.derecho.sandbox.base;

/**
 * Created by lugan on 10/8/2016.
 */
public interface HelloMBean {
    public void sayHello();

    public int add(int x, int y);

    public String getName();

    public int getCacheSize();

    public void setCacheSize(int size);

}

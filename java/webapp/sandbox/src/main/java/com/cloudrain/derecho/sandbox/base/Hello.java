package com.cloudrain.derecho.sandbox.base;

/**
 * Created by lugan on 10/8/2016.
 */
public class Hello implements HelloMBean {

    private final String name = "world";
    private static final int DEFAULT_CACHE_SIZE = 200;
    private int cacheSize = DEFAULT_CACHE_SIZE;


    @Override
    public void sayHello() {
        System.out.println("hello world");
    }

    @Override
    public int add(int x, int y) {
        return  x + y;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCacheSize() {
        return this.cacheSize;
    }

    @Override
    public void setCacheSize(int size) {
        this.cacheSize = size;
    }
}

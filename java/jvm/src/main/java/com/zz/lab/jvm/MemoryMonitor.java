package com.zz.lab.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-Xms100m -Xmx100m -XX:+UseSerialGC
 * run with JProfiler, and see memory usage:Eden, Suvivor, Tenured,10
 */
public class MemoryMonitor {
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            // 稍作延时，令监视曲线的变化更加明显
            Thread.sleep(100);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000);
    }
}

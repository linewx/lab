package com.cloudrain.derecho.sandbox.base;

import sun.util.resources.cldr.ga.LocaleNames_ga;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by lugan on 10/8/2016.
 */
public class Main {
    public static void main(String [] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName name = new ObjectName("com.cloudrain.derecho.sandbox.base:type=Hello");

        Hello mBean = new Hello();

        mbs.registerMBean(mBean, name);

        System.out.println("waiting forever ...");

        Thread.sleep(Long.MAX_VALUE);
    }
}

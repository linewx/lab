package com.cloudrain.derecho.sandbox.notification;
import javax.management.*;

/**
 * Created by lugan on 10/8/2016.
 */
public class Hello extends NotificationBroadcasterSupport implements HelloMBean {

    private final String name = "world";
    private static final int DEFAULT_CACHE_SIZE = 200;
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private long sequenceNumber = 1;


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
    public synchronized void setCacheSize(int size) {
        int oldSize = this.cacheSize;
        this.cacheSize = size;

        System.out.println("cache size now " + this.cacheSize);

        Notification notification = new AttributeChangeNotification(this,
                sequenceNumber++,
                System.currentTimeMillis(),
                "Cachesize changed",
                "CacheSzie",
                "int",
                oldSize,
                this.cacheSize);
        sendNotification(notification);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String [] types = new String[] {
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] {info};
    }
}

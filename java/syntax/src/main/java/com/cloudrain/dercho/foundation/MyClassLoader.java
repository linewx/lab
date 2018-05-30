package com.cloudrain.dercho.foundation;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

/**
 * Created by lugan on 8/24/2016.
 */
public class MyClassLoader {
    public static void printBootstrapClassPath() {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }

    public static void printSystemClassPath() {
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    public static void printClassLoadStack() throws Exception{
        ClassLoader loader = MyClassLoader.class.getClassLoader();    //获得加载ClassLoaderTest.class这个类的类加载器
        while(loader != null) {
            getAllClasses(loader);
            System.out.println(loader);
            loader = loader.getParent();    //获得父类加载器的引用
        }
        System.out.println(loader);
    }

    public static void getAllClasses(ClassLoader loader) throws Exception{
        Field f = ClassLoader.class.getDeclaredField("classes");
        f.setAccessible(true);

        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Vector<Class> classes =  (Vector<Class>) f.get(loader);
    }

    public static void main(String []argv) throws Exception{
        MyClassLoader.printBootstrapClassPath();
        printSystemClassPath();
        printClassLoadStack();
    }

}

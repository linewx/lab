package com.cloudrain.dercho.foundation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by lugan on 5/3/2016.
 */
public class ProxySample {
    public static void main(String ...argv) {
        Object[] elements = new Object[1000];

        for (int i=0; i<1000; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new ProxySample().new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null, new Class[] {Comparable.class}, handler);
            elements[i] = proxy;
        }

        Integer key = new Random().nextInt(elements.length) + 1;

        int result = Arrays.binarySearch(elements, key);

        if(result >= 0 ) {
            System.out.println(elements[result]);
        }
    }

    class TraceHandler implements InvocationHandler {
        private Object target;

        public TraceHandler(Object target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName());
            return method.invoke(target, args);
        }
    }
}

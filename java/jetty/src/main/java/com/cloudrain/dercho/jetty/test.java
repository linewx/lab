package com.cloudrain.dercho.jetty;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by luganlin on 4/21/17.
 */
public class test {
    public static void main(String ...argv) {
        Date currentDate = new Date();
        //long diff = d2.getTime() - d1.getTime();
        Date anotherDate = new Date(117, 3, 21);
        long diff = currentDate.getTime() - anotherDate.getTime();
        System.out.println(TimeUnit.DAYS.convert(-diff, TimeUnit.MILLISECONDS));
    }
}

package com.cloudrain.dercho.foundation.generictype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luganlin on 5/1/16.
 */
public class wildcardType {
    public static void main(String ...argv) {
        List list = new ArrayList<String>();
        list.add("aString");
        list.add(10);

        List<?> list1 = new ArrayList<String> ();
        //list1.add("aString"); //does not compile - we don't know it is a List<String>
        list1.clear(); //this is fine, does not depend on the generic parameter type
    }
}

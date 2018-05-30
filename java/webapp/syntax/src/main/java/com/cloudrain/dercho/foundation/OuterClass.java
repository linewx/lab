package com.cloudrain.dercho.foundation;

/**
 * Created by lugan on 4/20/2016.
 */
public class OuterClass {
    private String location ="I'm in outer class";

    private class InnerClass {
        private String location = "I'm in inner class";

        public void print(String location) {
            System.out.println(location);
            System.out.println(this.location);
            System.out.println(OuterClass.this.location);
        }
    }

    private static class StaticInnerClass {
        private String location = "i'm in inner class";
        public void print(String location) {
            System.out.println(location);
            System.out.println(this.location);
            //System.out.println(OuterClass.this.location);
        }
    }

    public static void main(String ...args) {
        InnerClass innerClass = new OuterClass().new InnerClass();
        innerClass.print("I'm in outside");

        StaticInnerClass staticInnerClass = new StaticInnerClass();
        staticInnerClass.print("i'm in outside");

        float a = 10000000000000l;
    }
}

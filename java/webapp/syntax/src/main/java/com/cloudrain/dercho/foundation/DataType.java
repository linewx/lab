package com.cloudrain.dercho.foundation;

import java.lang.reflect.Field;

/**
 * Created by luganlin on 4/21/16.
 */
public class DataType {
    private byte aByte;
    private short aShort;
    int anInt;
    boolean aBoolean;
    double aDouble;
    float aFloat;
    long aLong;
    char aChar;
    int[] aArray;

    public void printDefaultValue() {
        System.out.print("byte: ");
        System.out.println(aByte);
        System.out.print("short: ");
        System.out.println(aShort);
        System.out.print("int: ");
        System.out.println(anInt);
        System.out.print("float: ");
        System.out.println(aFloat);
        System.out.print("double: ");
        System.out.println(aDouble);
        System.out.print("long: ");
        System.out.println(aLong);
        System.out.print("char: ");
        System.out.println(aChar);
        System.out.print("boolean: ");
        System.out.println(aBoolean);
        System.out.println("");
    }

    public void printSize() {
        System.out.println("this is the size for primitive type:");
        System.out.println("byte: " + Byte.BYTES); //1
        System.out.println("short: " + Short.BYTES); //2
        System.out.println("int: " + Integer.BYTES); //4
        System.out.println("float: " + Float.BYTES); //4
        System.out.println("long: " + Long.BYTES); //8
        System.out.println("double: " + Double.BYTES); //8
        System.out.println("char: " + Character.BYTES); //2
        System.out.println("boolean: " + 1);
    }

    public void printRange() {
        System.out.println("this is the range for primitive type:");
        System.out.println("byte: " + Byte.MIN_VALUE + "~" + Byte.MAX_VALUE);
        System.out.println("short: " + Short.MIN_VALUE + "~" + Short.MAX_VALUE);
        System.out.println("int: " + Integer.MIN_VALUE + "~" + Integer.MAX_VALUE);
        System.out.println("float: " + Float.MIN_VALUE + "~" + Float.MAX_VALUE);
        System.out.println("long: " + Long.MIN_VALUE + "~" + Long.MAX_VALUE);
        System.out.println("double: " + Double.MIN_VALUE + "~" + Double.MAX_VALUE);
        System.out.println("char: " + Character.MIN_VALUE + "~" + Character.MAX_VALUE);
        System.out.println("boolean: true or false");


    }

    public void floatType() {
        //why does this happen? https://zh.wikipedia.org/wiki/IEEE_754
        System.out.println(Float.MAX_VALUE > Integer.MAX_VALUE);
        System.out.println(Float.MIN_VALUE > Integer.MIN_VALUE);
        System.out.println(1.0/0 == Float.POSITIVE_INFINITY);
        System.out.println(-1/0.0 == Float.NEGATIVE_INFINITY);
        double a = 0.0/0.0;
        System.out.println(Double.class.isInstance(1));
        System.out.println(3 == 3.01);
        System.out.println(0.0f/0.0f == Float.NaN);

        int b = 1;
        long c = b;
        float e = c;


    }

    public String detectLiteralType(Object literal) {
        String result = "";
        if(Byte.class.isInstance(literal)) {
            result = result + "Byte" + ",";
        }
        if(Boolean.class.isInstance(literal)) {
            result = result + "Boolean" + ",";
        }
        if(Character.class.isInstance(literal)) {
            result = result + "Character" + ",";
        }
        if(Integer.class.isInstance(literal)) {
            result = result + "Integer" + ",";
        }
        if(Short.class.isInstance(literal)) {
            result = result + "Short" + ",";
        }
        if(Long.class.isInstance(literal)) {
            result = result + "Long" + ",";
        }
        if(Float.class.isInstance(literal)) {
            result = result + "Float" + ",";
        }
        if(Double.class.isInstance(literal)) {
            result = result + "Double" + ",";
        }
        if(String.class.isInstance(literal)) {
            result = result + "String" + ",";
        }


        return result;

    }

    public void print(byte oneByte) {
        System.out.println("I'm a byte");
    }

    /*public void print(int oneInt) {
        System.out.println("im an int");
    }*/

    /*public void print(Integer oneInteger) {
        System.out.println("I'm an Integer");

    }*/

    /*public void print(long oneLong) {
        System.out.println("I'm a long");
    }*/

    public void print(float oneFloat) {
        System.out.println("I'm a float");
    }

    public void print(Object object) {
        System.out.println("I'm an object");
    }

    public void print(int a, long b) {
        System.out.println("i'm int and long");

    }

    public void print(long a, int b) {
        System.out.println("i'm long and long");
    }

    public void printLiteralTypes() {
        System.out.println("type of 0 is " + detectLiteralType(0));
        System.out.println("type of a is " + detectLiteralType('a'));
        System.out.println("type of hello is " + detectLiteralType("hello"));
        System.out.println("type of 1.0 is " + detectLiteralType(1.0));
        System.out.println("type of 1.0f is " + detectLiteralType(1.0f));
        System.out.println("type of 20d is " + detectLiteralType(20d));
        System.out.println("type of 0xCAFE_BABE is " + detectLiteralType(0xCAFEBABEL));

    }



    public static void main(String ...argv) throws ClassNotFoundException {
        DataType dataType = new DataType();
        dataType.printDefaultValue();
        dataType.printSize();
        dataType.printRange();

        byte e = 10;
        byte f = 11;
        //byte g = e + f; // 编译错误 +直接将10和11类型提升为了int

        dataType.floatType();
        dataType.printLiteralTypes();

        Field[] declaredFields = dataType.getClass().getDeclaredFields();
        Class<?> arrayClass = int[].class;
        Class a = DataType.class;
        System.out.println(arrayClass);
        Class<?> namedClass = Class.forName("[L" + String.class.getName() + ";");
        System.out.println(namedClass);
        System.out.println(arrayClass == namedClass);
        System.out.println(int.class.isInstance(1));
        byte c = 1;
        dataType.print(1);

    }
}

package com.cloudrain.dercho.foundation;

/**
 * Created by lugan on 4/22/2016.
 */
public class DataTypeConversion {
    private byte aByte;
    private char aChar;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;

    public void assignmentConversion(){
        //identity conversion
        anInt = 1;

        //widening primitive conversion
        //byte -> char -> short -> int -> long -> float -> double
        //forbidden: byte -> char & char -> short
        aDouble = aFloat;
        aDouble = aLong;
        aDouble = anInt;
        aDouble = aShort;
        aDouble = aChar;
        aDouble = aByte;

        aFloat = aLong;
        aFloat = anInt;
        aFloat = aShort;
        aFloat = aByte;

        aLong = anInt;
        aLong = aShort;
        aLong = aChar;
        aLong = aByte;

        anInt = aShort;
        anInt = aChar;
        anInt = aByte;

        /* char -> short is not permitted
        aShort = aChar;
        */
        aShort = aByte;

        /* byte -> char is not permitted
        aChar = aByte;
        */

        //widening reference conversion
        //todo


        //boxing conversion
        Integer anInteger = anInt;

        //unboxing conversion
        int bInt = anInteger;

        //In addition, if the expression is a constant expression (§15.28) of type byte, short, char, or int
        byte bByte = 10;
        char bChar = 100;
        short bShort = 100;
        /**** out of byte range, idenfied by compiler
            byte cByte = 1000;
            byte cByte = 10*10*10;
        ******/

    }



    public static void main(String ...argv) {
        /************************************
         * assignment conversion context ***
         * *********************************/

        //widening primitive conversion



    }
}

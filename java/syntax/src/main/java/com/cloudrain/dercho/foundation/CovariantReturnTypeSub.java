package com.cloudrain.dercho.foundation;

/**
 * Created by luganlin on 4/30/16.
 */
public class CovariantReturnTypeSub extends CovariantReturnType{
    public Integer getData() {
        return 1;
    }



    public static void main(String ...argv) {
        CovariantReturnType type = new CovariantReturnTypeSub();
        System.out.println(type.getData());

        CovariantReturnTypeSub subType = new CovariantReturnTypeSub();
        subType.getData();
    }
}

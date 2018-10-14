package com.zz.callstack.anonymous;

public class TargetClass implements Runnable{
    public void call(Runnable a) {
        a.run();
    }

    public void invokeLambda() {
        new TargetClass().call(() -> {
            System.out.print("lambda function");
        });
    }

    public void inovkeAnonyFunc() {
        new TargetClass().call(new Runnable() {
            @Override
            public void run() {
                System.out.print("anonymous function");
            }
        });
    }

    public void invokeAnonymousFunction() {
        new TargetClass().call(new Runnable() {
            @Override
            public void run() {
                System.out.print("anonymous function");
            }
        });
    }

    public void invokeFunction() {
        new TargetClass().call(new TargetClass());
    }

    @Override
    public void run() {
        System.out.println("normal function");
    }
}

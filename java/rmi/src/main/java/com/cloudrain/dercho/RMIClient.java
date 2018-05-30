package com.cloudrain.dercho;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by luganlin on 8/11/16.
 */
public class RMIClient {
    public static void main(String args[]) {
        try {
            //在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
            RMIHello rhello =(RMIHello) Naming.lookup("rmi://localhost:8888/RHello");
            System.out.println(rhello.helloWorld());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}

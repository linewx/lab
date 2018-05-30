package com.cloudrain.dercho;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by luganlin on 8/11/16.
 */
public class RMIServer extends UnicastRemoteObject implements RMIHello{

    @Override
    public String helloWorld() throws RemoteException{
        return "Hello World";
    }

    public RMIServer() throws RemoteException{
        super();
    }

    public static void main(String args[]) {
        try {
            RMIHello rhello = new RMIServer();
            LocateRegistry.createRegistry(8888);

            Naming.bind("rmi://localhost:8888/RHello",rhello);

            System.out.println(">>>>>INFO:远程IHello对象绑定成功！");
        } catch (RemoteException e) {
            System.out.println("创建远程对象发生异常！");
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            System.out.println("发生重复绑定对象异常！");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("发生URL畸形异常！");
            e.printStackTrace();
        }
    }
}

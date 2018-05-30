package com.cloudrain.dercho;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by luganlin on 8/11/16.
 */
public interface RMIHello extends Remote{
    String helloWorld() throws RemoteException;
}

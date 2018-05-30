package com.cloudrain.derecho.io.server;

import org.omg.SendingContext.RunTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lugan on 12/27/2016.
 */
public class ThreadPoolEchoServer {
    public static void main(String ...argv) throws Exception{
        int port = 5555;
        ServerSocket socket = new ServerSocket(port);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        while(true) {
            Socket clientSocket = socket.accept();
            executorService.submit(() -> {
                try {
                    BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                    String clientInput = clientReader.readLine();
                    Thread.sleep(2000);
                    clientWriter.println(clientInput);
                }catch (Exception e) {

                }finally {
                    /*try {
                        clientSocket.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        }
    }
}

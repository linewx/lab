package com.cloudrain.derecho.io.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lugan on 12/27/2016.
 */
public class SingleThreadEchoServer {
    public static void main(String ...argv) throws Exception{
        int port = 5555;
        ServerSocket socket = new ServerSocket(port);

        while(true) {
            Socket clientSocket = socket.accept();
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientInput = clientReader.readLine();
            //System.out.println(clientInput);
            Thread.sleep(2000);
            clientWriter.println(clientInput);
            //clientSocket.close();
        }
    }
}

package com.cloudrain.derecho.io.fundamental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lugan on 12/27/2016.
 */
public class EchoServer {
    public static void main(String ...argv) throws IOException{
        int port = 5555;
        try {
            ServerSocket listenSocket = new ServerSocket(port);
            Socket socket = listenSocket.accept();
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientWriter = new PrintWriter(socket.getOutputStream(), true);
            String clientInput;
            while((clientInput = clientReader.readLine()) != null) {
               System.out.println("received message:" + clientInput);
                clientWriter.println(clientInput);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}

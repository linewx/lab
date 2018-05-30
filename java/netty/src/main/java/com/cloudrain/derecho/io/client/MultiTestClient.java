package com.cloudrain.derecho.io.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lugan on 12/27/2016.
 */
public class MultiTestClient{
    private static CountDownLatch latch = new CountDownLatch(10);

    public static void main(String ...argv) throws Exception{
        Runnable oneClient = () -> {
            int port = 5555;
            String host = "localhost";
            Socket socket;
            try {
                socket = new Socket(host, port);
                //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

               // String userInput = stdIn.readLine();
                serverWriter.println("this is the test words");
                String echoString = serverReader.readLine();
                System.out.println("received string " + echoString);
                socket.close();
            }catch (IOException e) {
                //e.printStackTrace();
            } finally {
                latch.countDown();
            }
        };


        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(oneClient).start();
        }

        latch.await();

        long endTime = System.currentTimeMillis();

        System.out.println("cost second(s): " + (endTime - startTime)/1000);
    }

}

package com.cloudrain.derecho.io.fundamental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by lugan on 12/27/2016.
 */
public class EchoClient {

    public static void main(String ...argv) throws IOException{
        String hostName = "localhost";
        int port = 5555;
        try {
            Socket echoSocket = new Socket(hostName, port);
            PrintWriter serverWriter = new PrintWriter(echoSocket.getOutputStream(), true);

            BufferedReader serverReader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while( (userInput = stdReader.readLine()) != null) {
                serverWriter.println(userInput);
                System.out.println(serverReader.readLine());
            }
        }catch (UnknownHostException e) {
            System.err.println("Donn't know about host" + hostName);
            System.exit(1);
        }catch (IOException e) {
            System.err.println("Could't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}

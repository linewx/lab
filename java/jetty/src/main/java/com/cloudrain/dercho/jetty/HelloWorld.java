package com.cloudrain.dercho.jetty;

/**
 * Created by lugan on 12/1/2015.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

public class HelloWorld extends AbstractHandler
{


    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("{\"entityType\": \"ActualService\"}");

    }


    public static void main(String[] args) throws Exception
    {
        //Long a = Long.parseLong(null);
        //System.out.println(a);
        Long a = (Long)null;
        System.out.println(InetAddress.getLocalHost().getCanonicalHostName());

        /*ArrayList<String> arrayList = new ArrayList<String>();
        Server server = new Server();

        // Add a single handler on context "/hello"
        ContextHandler context = new ContextHandler();
        context.setContextPath( "/hello" );
        context.setHandler(new HelloWorld());

        ServerConnector http = new ServerConnector(server);
        http.setHost("0.0.0.0");
        http.setPort(7777);
        http.setIdleTimeout(30000);

        // Set the connector
        server.addConnector(http);
        server.setHandler( context );


        try {


            server.start();
            server.join();
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("new exception");
        }finally {
            server.stop();
        }

        System.out.println("isFailed" + server.isFailed());
        System.out.println("isRunning" + server.isRunning());
        System.out.println("isStarted" + server.isStarted());
        System.out.println("isStarting" + server.isStarting());
        System.out.println("isStopped" + server.isStopped());
        System.out.println("isStopping" + server.isStopping());*/

        System.out.println(Byte.TYPE);
        System.out.println(Byte.SIZE);
        System.out.println(Byte.MIN_VALUE);
        System.out.println(Byte.MAX_VALUE);

        System.out.println(Short.TYPE);
        System.out.println(Short.SIZE);
        System.out.println(Short.MIN_VALUE);
        System.out.println(Short.MAX_VALUE);

        System.out.println(Character.TYPE);
        System.out.println(Character.SIZE);
        System.out.println((int) Character.MIN_VALUE);
        System.out.println((int) Character.MAX_VALUE);

        System.out.println(Integer.TYPE);
        System.out.println(Integer.SIZE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);

        System.out.println(Float.TYPE);
        System.out.println(Float.SIZE);
        System.out.println(Float.MIN_VALUE);
        System.out.println(Float.MAX_VALUE);

        System.out.println(Double.TYPE);
        System.out.println(Double.SIZE);
        System.out.println(Double.MIN_VALUE);
        System.out.println(Double.MAX_VALUE);

        System.out.println(Long.TYPE);
        System.out.println(Long.SIZE);
        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);


    }
}
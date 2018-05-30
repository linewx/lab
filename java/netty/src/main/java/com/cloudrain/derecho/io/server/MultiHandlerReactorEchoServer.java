package com.cloudrain.derecho.io.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lugan on 12/28/2016.
 */
public class MultiHandlerReactorEchoServer {

    public static class Processor {
        public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        public static void process(SelectionKey key) {
            Runnable runnable = () -> {
                SocketChannel socketChannel = null;
                try {
                    socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int count = socketChannel.read(byteBuffer);
                    if (count < 0) {
                        socketChannel.close();
                        key.cancel();
                    } else if (count == 0) {
                        return;
                    } else {
                        Thread.sleep(2000);
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        System.out.println(byteBuffer.toString());
                        byteBuffer.clear();
                    }

                } catch (Exception e) {
                    try {
                        socketChannel.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }


            };
            executorService.submit(runnable);
            //runnable.run();
        }
    }

    public static void main(String... argv) throws Exception {
        int port = 5555;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            System.out.println("select again");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel clientSocketChannel = socketChannel.accept();
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    Processor.process(key);
                }
            }

        }
    }

}

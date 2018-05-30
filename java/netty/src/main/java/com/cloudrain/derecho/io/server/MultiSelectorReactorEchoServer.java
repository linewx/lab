package com.cloudrain.derecho.io.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lugan on 12/28/2016.
 */
public class MultiSelectorReactorEchoServer {
    public static class Processor {
        private ExecutorService executorService = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

        private Selector selector;

        public Processor() throws Exception{
            this.selector = SelectorProvider.provider().openSelector();
            process();
        }

        public void addChannel(SocketChannel socketChannel) throws ClosedChannelException{
            socketChannel.register(selector, SelectionKey.OP_READ);

        }

        public void process(){
            executorService.submit(() -> {
                try {
                    while(true) {
                        if (selector.selectNow() <=0 ) {
                            continue;
                        }
                         Set<SelectionKey> keys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = keys.iterator();
                        while(iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            SocketChannel socketChannel = (SocketChannel)key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int count = socketChannel.read(byteBuffer);
                            if (count < 0) {
                                socketChannel.close();
                                key.cancel();
                                continue;
                            }else if(count == 0) {
                                continue;
                            }else {
                                Thread.sleep(2000);
                                byteBuffer.flip();
                                socketChannel.write(byteBuffer);
                                byteBuffer.clear();
                            }
                        }
                    }
                }catch (Exception e) {

                }

            });
        }
    }





    public static void main(String ...argv) throws Exception{
        int port = 5555;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int threadNumber = Runtime.getRuntime().availableProcessors();
        Processor[] processors = new Processor[threadNumber];
        for (int i=0; i<processors.length; i++) {
            processors[i] = new Processor();
        }

        int index = 0;
        while(selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key: keys) {
                keys.remove(key);
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptSocketChannel = (ServerSocketChannel)key.channel();
                    SocketChannel socketChannel = acceptSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    Processor processor =  processors[((index++) / threadNumber)];
                    processor.addChannel(socketChannel);
                }
            }
        }

    }
}

package com.cloudrain.derecho.ds.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lugan on 12/21/2016.
 */
public class Example {
    public static void main(String ...argv) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Disruptor<ValueEvent> disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 1024, exec);

        final EventHandler<ValueEvent> handler = (ValueEvent event, long sequence, boolean endOfBatch) -> {
            System.out.println("Sequence: " + sequence);
            System.out.println("ValueEvent: " + event.getValue());
        };

        disruptor.handleEventsWith(handler);

        RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        for (long i = 0; i < 2000; i++) {
            String uuid = UUID.randomUUID().toString();
            long seq = ringBuffer.next();
            ValueEvent valueEvent = ringBuffer.get(seq);
            valueEvent.setValue(uuid);
            ringBuffer.publish(seq);
        }

        disruptor.shutdown();
        exec.shutdown();

    }
}

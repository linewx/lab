package com.cloudrain.derecho.ds.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by lugan on 12/21/2016.
 */
public final class ValueEvent {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = ValueEvent::new;
}

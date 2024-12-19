package com.zzh.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 17:59
 */
public class Publisher {

    private final List<Listener> listeners = new ArrayList<>();

    public void publishEvent(Event event) {
        for (Listener listener : listeners) {
            listener.onEvent(event);
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

}

package com.geode.core.registry;


import com.geode.core.Initializable;

import java.util.HashMap;

public abstract class Registry<T> implements Initializable {
    private final HashMap<Class<? extends T>, T> data = new HashMap<>();

    public Registry<T> register(T value) {
        data.put((Class<? extends T>) value.getClass(), value);
        return this;
    }

    public <U extends T> U get(Class<U> clazz) {
        return (U) data.get(clazz);
    }
}

package com.geode.test;

import com.geode.core.reflections.Singleton;

@Singleton
public final class MySingleton {

    private static MySingleton instance;

    private MySingleton() {

    }

    public static MySingleton getInstance() {
        if(instance == null)
            instance = new MySingleton();
        return instance;
    }

    public void sayHello() {
        System.err.println("Hello !");
    }
}

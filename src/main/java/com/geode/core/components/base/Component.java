package com.geode.core.components.base;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.core.Taggable;
import com.geode.entity.GameObject;

public abstract class Component implements Taggable, Initializable, Closeable {

    public static final int MIN_PRIORITY = Integer.MIN_VALUE;
    public static final int MAX_PRIORITY = Integer.MAX_VALUE;

    private int priority = 0;
    protected final GameObject child;

    public Component(GameObject child) {
        this.child = child;
    }

    public GameObject getChild() {
        return child;
    }

    @Override
    public Object tag() {
        return child;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        child.sortComponents();
    }

    public int getPriority() {
        return priority;
    }
}

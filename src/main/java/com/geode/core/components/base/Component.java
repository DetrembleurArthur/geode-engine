package com.geode.core.components.base;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.core.Taggable;
import com.geode.entity.GameObject;

public abstract class Component implements Taggable, Initializable, Closeable {

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
}

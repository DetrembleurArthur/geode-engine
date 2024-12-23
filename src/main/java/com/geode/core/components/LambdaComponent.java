package com.geode.core.components;

import com.geode.core.components.base.UpdateableComponent;
import com.geode.entity.GameObject;
import com.geode.exceptions.GeodeException;

public class LambdaComponent extends UpdateableComponent {

    private Runnable runnable;

    public LambdaComponent(GameObject child) {
        super(child);
    }

    public void set(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void close() throws Exception {
        runnable = null;
    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        if (runnable != null) {
            runnable.run();
        }
    }
}

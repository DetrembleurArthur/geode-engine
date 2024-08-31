package com.geode.test;

import com.geode.core.*;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.winevents.OnWindowCursorEnterEventHandler;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

@SceneEntry(value = "my_scene", first = true)
public class MyScene extends Scene {

    public @Inject Application app;
    public @Inject WindowManager windowManager;
    public @Inject SceneManager sceneManager;
    public @Inject MySingleton singleton;
    public @Inject WindowEventsManager eventsManager;

    @Override
    public void init() {
        windowManager.getWindow().setClearColor(new Vector4f(1, 0, 0, 1));
        singleton.sayHello();
        eventsManager.getWindowKeyEventHandler().setConcurrentProof(true);
        eventsManager.onCursorEntered(new OnWindowCursorEnterEventHandler.CursorEnteredCallback() {
            @Override
            public void trigger() {
                System.err.println("cursor entered");
                eventsManager.getWindowCursorEnterEventHandler().removeByTag(this);
            }

            @Override
            public Object tag() {
                System.err.println("TAG: " + this);
                return this;
            }
        });
    }

    @Override
    public void initResources() {

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(double dt) {

    }

    @Override
    public void close() throws Exception {

    }
}

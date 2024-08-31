package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;

public class OnWindowContentScaleEventHandler extends WindowCallbacksHandler implements GLFWWindowContentScaleCallbackI {
    public OnWindowContentScaleEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowContentScaleCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFW.glfwSetWindowContentScaleCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface ScaleXCallback extends WindowCallback {
        void trigger(Float x);
    }

    public interface ScaleYCallback extends WindowCallback {
        void trigger(Float y);
    }

    public interface ScaleCallback extends WindowCallback {
        void trigger(Vector2f scale);
    }

    private Vector2f scale = new Vector2f();

    @Override
    public void invoke(long window, float scalex, float scaley) {
        if(scale.x != scalex) {
            trigger(ScaleXCallback.class, scalex);
        }
        if(scale.y != scaley) {
            trigger(ScaleYCallback.class, scaley);
        }
        if(scale.x != scalex || scale.y != scaley) {
            trigger(ScaleCallback.class, new Vector2f(scalex, scaley));
        }
        scale = new Vector2f(scalex, scaley);
    }
}

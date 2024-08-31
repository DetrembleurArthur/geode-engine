package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class OnWindowCursorPosEventHandler extends WindowCallbacksHandler implements GLFWCursorPosCallbackI {
    public OnWindowCursorPosEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetCursorPosCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFW.glfwSetCursorPosCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface CursorXCallback extends WindowCallback {
        void trigger(Double x);
    }

    public interface CursorYCallback extends WindowCallback {
        void trigger(Double y);
    }

    public interface CursorPosCallback extends WindowCallback {
        void trigger(Vector2f pos);
    }

    private Vector2f position = new Vector2f();

    @Override
    public void invoke(long window, double x, double y) {
        if(position.x != x) {
            trigger(CursorXCallback.class, x);
        }
        if(position.y != y) {
            trigger(CursorYCallback.class, y);
        }
        if(position.x != x || position.y != y) {
            trigger(CursorPosCallback.class, new Vector2f((float) x, (float) y));
        }
        position = new Vector2f((float) x, (float) y);
    }
}

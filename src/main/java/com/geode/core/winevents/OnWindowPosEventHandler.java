package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public class OnWindowPosEventHandler extends WindowCallbacksHandler implements GLFWWindowPosCallbackI {
    public OnWindowPosEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowPosCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFW.glfwSetWindowPosCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface XCallback extends WindowCallback {
        void trigger(Integer x);
    }

    public interface YCallback extends WindowCallback {
        void trigger(Integer y);
    }

    public interface PositionCallback extends WindowCallback {
        void trigger(Vector2i position);
    }

    private Vector2i position = new Vector2i();

    @Override
    public void invoke(long window, int x, int y) {
        if(position.x != x) {
            trigger(XCallback.class, x);
        }
        if(position.y != y) {
            trigger(YCallback.class, y);
        }
        if(position.x != x || position.y != y) {
            trigger(PositionCallback.class, new Vector2f(x, y));
        }
        position = new Vector2i(x, y);
    }
}

package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public class OnWindowFramebufferSizeEventHandler extends WindowCallbacksHandler implements GLFWFramebufferSizeCallbackI {

    public OnWindowFramebufferSizeEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetFramebufferSizeCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFW.glfwSetFramebufferSizeCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface FbWidthCallback extends WindowCallback {
        void trigger(Integer width);
    }

    public interface FbHeightCallback extends WindowCallback {
        void trigger(Integer width);
    }

    public interface FbSizeCallback extends WindowCallback {
        void trigger(Vector2i size);
    }

    private Vector2i previousSize = new Vector2i();

    @Override
    public void invoke(long window, int width, int height) {
        if(width != previousSize.x) {
            trigger(FbWidthCallback.class, width);
        }
        if(height != previousSize.y) {
            trigger(FbHeightCallback.class, height);
        }
        if(width != previousSize.x || height != previousSize.y) {
            trigger(FbSizeCallback.class, new Vector2i(width, height));
        }
        previousSize = new Vector2i(width, height);
    }
}

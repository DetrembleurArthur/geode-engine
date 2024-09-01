package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
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
        GLFWFramebufferSizeCallback callback = GLFW.glfwSetFramebufferSizeCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    private Vector2i previousSize = new Vector2i();

    @Override
    public void invoke(long window, int width, int height) {
        if(width != previousSize.x) {
            trigger(WinEvents.FbWidthCallback.class, width);
        }
        if(height != previousSize.y) {
            trigger(WinEvents.FbHeightCallback.class, height);
        }
        if(width != previousSize.x || height != previousSize.y) {
            trigger(WinEvents.FbSizeCallback.class, new Vector2i(width, height));
        }
        previousSize = new Vector2i(width, height);
    }
}

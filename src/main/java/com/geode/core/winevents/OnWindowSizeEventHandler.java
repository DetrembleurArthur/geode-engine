package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public class OnWindowSizeEventHandler extends WindowCallbacksHandler implements GLFWWindowSizeCallbackI {

    public OnWindowSizeEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowSizeCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWWindowSizeCallback callback = GLFW.glfwSetWindowSizeCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    private Vector2i previousSize = new Vector2i();

    @Override
    public void invoke(long window, int width, int height) {
        if(width != previousSize.x) {
            trigger(WinEvents.WidthCallback.class, width);
        }
        if(height != previousSize.y) {
            trigger(WinEvents.HeightCallback.class, height);
        }
        if(width != previousSize.x || height != previousSize.y) {
            trigger(WinEvents.SizeCallback.class, new Vector2i(width, height));
        }
        previousSize = new Vector2i(width, height);
    }
}

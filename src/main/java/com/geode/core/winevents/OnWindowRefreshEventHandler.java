package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;

public class OnWindowRefreshEventHandler extends WindowCallbacksHandler implements GLFWWindowRefreshCallbackI {
    public OnWindowRefreshEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowRefreshCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFW.glfwSetWindowRefreshCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface RefreshCallback extends WindowCallback {
        void trigger();
    }

    @Override
    public void invoke(long window) {
        trigger(RefreshCallback.class);
    }
}

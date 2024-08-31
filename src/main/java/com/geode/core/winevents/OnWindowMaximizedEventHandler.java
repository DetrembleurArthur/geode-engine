package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallback;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public class OnWindowMaximizedEventHandler extends WindowCallbacksHandler implements GLFWWindowMaximizeCallbackI {
    public OnWindowMaximizedEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowMaximizeCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWWindowMaximizeCallback callback = GLFW.glfwSetWindowMaximizeCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    public interface MaximizedCallback extends WindowCallback {
        void trigger();
    }

    public interface MinimizedCallback extends WindowCallback {
        void trigger();
    }

    @Override
    public void invoke(long window, boolean maximized) {
        if(maximized) {
            trigger(MaximizedCallback.class);
        } else {
            trigger(MinimizedCallback.class);
        }
    }
}

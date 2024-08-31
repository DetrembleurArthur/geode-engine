package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public class OnWindowFocusedEventHandler extends WindowCallbacksHandler implements GLFWWindowFocusCallbackI {
    public OnWindowFocusedEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowFocusCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWWindowFocusCallback callback = GLFW.glfwSetWindowFocusCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    public interface FocusedCallback extends WindowCallback {
        void trigger();
    }

    public interface UnfocusedCallback extends WindowCallback {
        void trigger();
    }

    @Override
    public void invoke(long window, boolean focused) {
        if(focused) {
            trigger(FocusedCallback.class);
        } else {
            trigger(UnfocusedCallback.class);
        }
    }
}

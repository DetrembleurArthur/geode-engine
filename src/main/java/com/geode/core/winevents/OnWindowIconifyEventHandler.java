package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public class OnWindowIconifyEventHandler extends WindowCallbacksHandler implements GLFWWindowIconifyCallbackI {
    public OnWindowIconifyEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowIconifyCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWWindowIconifyCallback callback = GLFW.glfwSetWindowIconifyCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    @Override
    public void invoke(long window, boolean iconify) {
        if(iconify) {
            trigger(WinEvents.IconifyCallback.class);
        } else {
            trigger(WinEvents.RestoreCallback.class);
        }
    }
}

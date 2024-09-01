package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

public class OnWindowCloseEventHandler extends WindowCallbacksHandler implements GLFWWindowCloseCallbackI {

    public OnWindowCloseEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowCloseCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() {
        GLFWWindowCloseCallback callback = GLFW.glfwSetWindowCloseCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }



    @Override
    public void invoke(long window) {
        trigger(WinEvents.CloseCallback.class);
    }
}

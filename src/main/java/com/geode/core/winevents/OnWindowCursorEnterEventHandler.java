package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public class OnWindowCursorEnterEventHandler extends WindowCallbacksHandler implements GLFWCursorEnterCallbackI {
    public OnWindowCursorEnterEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetCursorEnterCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWCursorEnterCallback callback = GLFW.glfwSetCursorEnterCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    @Override
    public void invoke(long window, boolean entered) {
        if(entered) {
            trigger(WinEvents.CursorEnteredCallback.class);
        } else {
            trigger(WinEvents.CursorExitedCallback.class);
        }
    }
}

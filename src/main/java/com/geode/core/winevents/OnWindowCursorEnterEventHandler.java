package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
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
        GLFW.glfwSetCursorEnterCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface CursorEnteredCallback extends WindowCallback {
        void trigger();
    }

    public interface CursorExitedCallback extends WindowCallback {
        void trigger();
    }

    @Override
    public void invoke(long window, boolean entered) {
        if(entered) {
            trigger(CursorEnteredCallback.class);
        } else {
            trigger(CursorExitedCallback.class);
        }
    }
}

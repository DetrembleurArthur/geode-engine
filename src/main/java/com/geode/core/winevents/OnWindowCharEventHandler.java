package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCharCallbackI;

public class OnWindowCharEventHandler extends WindowCallbacksHandler implements GLFWCharCallbackI {
    public OnWindowCharEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetCharCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWCharCallback callback = GLFW.glfwSetCharCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    public interface CharCallback extends WindowCallback {
        void trigger(Character character);
    }

    @Override
    public void invoke(long window, int character) {
        trigger(CharCallback.class, (char) character);
    }
}

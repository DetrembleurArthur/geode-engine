package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.core.winevents.key.KeyInput;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class OnWindowKeyEventHandler extends WindowCallbacksHandler implements GLFWKeyCallbackI {
    public OnWindowKeyEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetKeyCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFW.glfwSetKeyCallback(windowEventManager.getWindow().getPointer(), null);
    }

    public interface KeyCallback extends WindowCallback {
        void trigger(KeyInput keyInput);
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        KeyInput keyInput = new KeyInput(key, scancode, action, mods);
        trigger(KeyCallback.class, keyInput);
    }
}

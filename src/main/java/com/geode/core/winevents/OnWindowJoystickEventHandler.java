package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWJoystickCallback;
import org.lwjgl.glfw.GLFWJoystickCallbackI;

public class OnWindowJoystickEventHandler extends WindowCallbacksHandler implements GLFWJoystickCallbackI {
    public OnWindowJoystickEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetJoystickCallback(this);
    }

    @Override
    public void close() throws Exception {
        GLFWJoystickCallback callback = GLFW.glfwSetJoystickCallback(null);
        if(callback != null)
            callback.free();
    }

    public interface JoystickConnectedCallback extends WindowCallback {
        void trigger();
    }

    public interface JoystickDisconnectedCallback extends WindowCallback {
        void trigger();
    }

    @Override
    public void invoke(int window, int event) {
        if(event == GLFW.GLFW_CONNECTED) {
            trigger(JoystickConnectedCallback.class);
        } else if(event == GLFW.GLFW_DISCONNECTED) {
            trigger(JoystickDisconnectedCallback.class);
        }
    }
}

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

    @Override
    public void invoke(int jid, int event) {
        if(event == GLFW.GLFW_CONNECTED) {
            trigger(WinEvents.JoystickConnectedCallback.class, jid);
        } else if(event == GLFW.GLFW_DISCONNECTED) {
            trigger(WinEvents.JoystickDisconnectedCallback.class, jid);
        }
    }
}

package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

public class OnWindowMonitorEventHandler extends WindowCallbacksHandler implements GLFWMonitorCallbackI {
    public OnWindowMonitorEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetMonitorCallback(this);
    }

    @Override
    public void close() throws Exception {
        GLFWMonitorCallback callback = GLFW.glfwSetMonitorCallback(null);
        if(callback != null)
            callback.free();
    }

    @Override
    public void invoke(long window, int event) {
        if(event == GLFW.GLFW_CONNECTED) {
            trigger(WinEvents.MonitorConnected.class);
        } else if(event == GLFW.GLFW_DISCONNECTED) {
            trigger(WinEvents.MonitorDisconnected.class);
        }
    }
}

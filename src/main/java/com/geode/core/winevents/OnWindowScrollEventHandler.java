package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public class OnWindowScrollEventHandler extends WindowCallbacksHandler implements GLFWScrollCallbackI {
    public OnWindowScrollEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetScrollCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWScrollCallback callback = GLFW.glfwSetScrollCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    private Vector2f scroll = new Vector2f();

    @Override
    public void invoke(long window, double scrollx, double scrolly) {
        if(scroll.x != scrollx) {
            trigger(WinEvents.ScrollXCallback.class, scrollx);
        }
        if(scroll.y != scrolly) {
            trigger(WinEvents.ScrollYCallback.class, scrolly);
        }
        if(scroll.x != scrollx || scroll.y != scrolly) {
            trigger(WinEvents.ScrollCallback.class, new Vector2f((float) scrollx, (float) scrolly));
        }
        scroll = new Vector2f((float) scrollx, (float) scrolly);
    }
}

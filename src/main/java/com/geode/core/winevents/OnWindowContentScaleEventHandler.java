package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowContentScaleCallback;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;

public class OnWindowContentScaleEventHandler extends WindowCallbacksHandler implements GLFWWindowContentScaleCallbackI {
    public OnWindowContentScaleEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetWindowContentScaleCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWWindowContentScaleCallback callback = GLFW.glfwSetWindowContentScaleCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }



    private Vector2f scale = new Vector2f();

    @Override
    public void invoke(long window, float scalex, float scaley) {
        if(scale.x != scalex) {
            trigger(WinEvents.ScaleXCallback.class, scalex);
        }
        if(scale.y != scaley) {
            trigger(WinEvents.ScaleYCallback.class, scaley);
        }
        if(scale.x != scalex || scale.y != scaley) {
            trigger(WinEvents.ScaleCallback.class, new Vector2f(scalex, scaley));
        }
        scale = new Vector2f(scalex, scaley);
    }
}

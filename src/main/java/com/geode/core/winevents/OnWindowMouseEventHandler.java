package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.core.winevents.mouse.MouseInput;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class OnWindowMouseEventHandler extends WindowCallbacksHandler implements GLFWMouseButtonCallbackI {
    public OnWindowMouseEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetMouseButtonCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWMouseButtonCallback callback = GLFW.glfwSetMouseButtonCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    public interface MouseCallback extends WindowCallback {
        void trigger(MouseInput mouseInput);
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        MouseInput mouseInput = new MouseInput(button, action, mods);
        trigger(MouseCallback.class, mouseInput);
    }
}

package com.geode.core.winevents;

import com.geode.core.WindowEventsManager;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.system.Pointer;

import static org.lwjgl.system.MemoryUtil.memGetAddress;
import static org.lwjgl.system.MemoryUtil.memUTF8;

public class OnWindowDropEventHandler extends WindowCallbacksHandler implements GLFWDropCallbackI {
    public OnWindowDropEventHandler(WindowEventsManager windowEventManager) {
        super(windowEventManager);
    }

    @Override
    public void init() throws GeodeException {
        GLFW.glfwSetDropCallback(windowEventManager.getWindow().getPointer(), this);
    }

    @Override
    public void close() throws Exception {
        GLFWDropCallback callback = GLFW.glfwSetDropCallback(windowEventManager.getWindow().getPointer(), null);
        if(callback != null)
            callback.free();
    }

    @Override
    public void invoke(long window, int count, long paths) {
        String[] items = new String[count];
        for(int i = 0; i < count; i++)
        {
            items[i] = memUTF8(memGetAddress(paths + (long) Pointer.POINTER_SIZE * i));
        }
        trigger(WinEvents.DropCallback.class, (Object) items);
    }
}

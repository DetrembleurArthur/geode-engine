package com.geode.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class Clipboard
{
    public static void set(String text)
    {
        GLFW.glfwSetClipboardString(MemoryUtil.NULL, text);
    }

    public static String get()
    {
        return GLFW.glfwGetClipboardString(MemoryUtil.NULL);
    }
}

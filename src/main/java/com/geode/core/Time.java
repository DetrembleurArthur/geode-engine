package com.geode.core;

import org.lwjgl.glfw.GLFW;

public class Time {
    private static double delta = 0;
    private static double lastTimestamp = 0;

    static {
        lastTimestamp = get_sec();
    }

    static double auto_update_delta() {
        double t1 = get_sec();
        delta = t1 - lastTimestamp;
        lastTimestamp = t1;
        return delta;
    }

    public static double get_sec() {
        return GLFW.glfwGetTime();
    }

    public static void set(double time) {
        GLFW.glfwSetTime(time);
    }

    public static long getTimerValue() {
        return GLFW.glfwGetTimerValue();
    }

    public static long getTimerFreq() {
        return GLFW.glfwGetTimerFrequency();
    }

    public static void setDelta(double delta) {
        Time.delta = delta;
    }

    public static double getDelta() {
        return Time.delta;
    }
}

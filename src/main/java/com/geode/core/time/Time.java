package com.geode.core.time;

import org.lwjgl.glfw.GLFW;

public class Time {
    private static double delta = 0;
    private static double lastTimestamp = 0;
    private static double current = 0;

    static {
        lastTimestamp = get_sec();
    }

    public static double auto_update_delta() {
        current = get_sec();
        delta = current - lastTimestamp;
        lastTimestamp = current;
        return delta;
    }

    public static void waitForTargetFps(int fps) {
        long sleepTime = (long) ((lastTimestamp - get_sec() + (1.0 / fps)) * 1000);
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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

    public static float deltify(float value) {
        return (float) (value * Time.delta);
    }

    public static double deltify(double value) {
        return value * Time.delta;
    }

    public static int getCurrentFps() {
        return (int) (1.0 / delta);
    }
}

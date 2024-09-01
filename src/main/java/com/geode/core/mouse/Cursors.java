package com.geode.core.mouse;

import static org.lwjgl.glfw.GLFW.*;

public enum Cursors
    {
        DEFAULT(0),
        ARROW(GLFW_ARROW_CURSOR),
        POINTING_HAND(GLFW_POINTING_HAND_CURSOR),
        IBEAM(GLFW_IBEAM_CURSOR),
        CROSSAIR(GLFW_CROSSHAIR_CURSOR),
        HRESIZE(GLFW_RESIZE_EW_CURSOR),
        VRESIZE(GLFW_RESIZE_NS_CURSOR),
        RESIZE_NWSE(GLFW_RESIZE_NWSE_CURSOR),
        RESIZE_NESW(GLFW_RESIZE_NESW_CURSOR),
        RESIZE_ALL(GLFW_RESIZE_ALL_CURSOR),
        NOT_ALLOWED(GLFW_NOT_ALLOWED_CURSOR);

        private final int glfwId;

        Cursors(int glfwId) {
            this.glfwId = glfwId;
        }

        public int getGlfwId() {
            return glfwId;
        }
    };
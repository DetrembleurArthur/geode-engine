package com.geode.core.key;

import org.lwjgl.glfw.GLFW;

public enum KeyMods {

        SHIFT(GLFW.GLFW_MOD_SHIFT),
        CONTROL(GLFW.GLFW_MOD_CONTROL),
        ALT(GLFW.GLFW_MOD_ALT),
        SUPER(GLFW.GLFW_MOD_SUPER),
        CAPS_LOCK(GLFW.GLFW_MOD_CAPS_LOCK),
        NUM_LOCK(GLFW.GLFW_MOD_NUM_LOCK);

        private final int glfwId;

        KeyMods(int glfwId) {
            this.glfwId = glfwId;
        }

        public int getGlfwId() {
            return glfwId;
        }
    }
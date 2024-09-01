package com.geode.core.key;

import org.lwjgl.glfw.GLFW;

public enum KeyState {

        PRESSED(GLFW.GLFW_PRESS),
        RELEASED(GLFW.GLFW_RELEASE),
        REPEATED(GLFW.GLFW_REPEAT);

        private final int glfwId;

        KeyState(int glfwId) {
            this.glfwId = glfwId;
        }

        public int getGlfwId() {
            return glfwId;
        }

        public static KeyState convert(int glfwState) {
            switch (glfwState) {
                case GLFW.GLFW_PRESS:
                    return PRESSED;
                case GLFW.GLFW_RELEASE:
                    return RELEASED;
                default:
                    return REPEATED;
            }
        }
    }
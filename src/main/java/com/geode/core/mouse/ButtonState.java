package com.geode.core.mouse;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public enum ButtonState {
        PRESSED(GLFW_PRESS),
        RELEASED(GLFW_RELEASE);

        ButtonState(int glfwId) {
            this.glfwId = glfwId;
        }
        private final int glfwId;

        public int getGlfwId() {
            return glfwId;
        }
    }
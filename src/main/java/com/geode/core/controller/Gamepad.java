package com.geode.core.controller;

import org.lwjgl.glfw.GLFW;

public enum Gamepad {

        A(GLFW.GLFW_GAMEPAD_BUTTON_A),
        CROSS(GLFW.GLFW_GAMEPAD_BUTTON_CROSS),
        B(GLFW.GLFW_GAMEPAD_BUTTON_B),
        CIRCLE(GLFW.GLFW_GAMEPAD_BUTTON_CIRCLE),
        X(GLFW.GLFW_GAMEPAD_BUTTON_X),
        SQUARE(GLFW.GLFW_GAMEPAD_BUTTON_SQUARE),
        Y(GLFW.GLFW_GAMEPAD_BUTTON_Y),
        TRIANGLE(GLFW.GLFW_GAMEPAD_BUTTON_TRIANGLE),
        LB(GLFW.GLFW_GAMEPAD_BUTTON_LEFT_BUMPER),
        RB(GLFW.GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER),
        SELECT(GLFW.GLFW_GAMEPAD_BUTTON_BACK),
        START(GLFW.GLFW_GAMEPAD_BUTTON_START),
        L(GLFW.GLFW_GAMEPAD_BUTTON_LEFT_THUMB),
        R(GLFW.GLFW_GAMEPAD_BUTTON_RIGHT_THUMB),
        UP(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP),
        RIGHT(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT),
        DOWN(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN),
        LEFT(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT),

        AXIS_LEFT_X(GLFW.GLFW_GAMEPAD_AXIS_LEFT_X),
        AXIS_LEFT_Y(GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y),
        AXIS_RIGHT_X(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_X),
        AXIS_RIGHT_Y(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_Y),
        AXIS_LT(GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER),
        AXIS_RT(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);


        private final int glfwId;

        Gamepad(int glfwId) {
            this.glfwId = glfwId;
        }

        public int getGlfwId() {
            return glfwId;
        }
    }
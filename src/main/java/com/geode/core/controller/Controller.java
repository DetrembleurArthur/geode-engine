package com.geode.core.controller;

import com.geode.core.Updateable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Controller implements Updateable {
    private int jid;
    private float[] axes = null;
    private byte[] buttons = null;
    private byte[] hats = null;

    private float[] gamepadAxes = null;
    private byte[] gamepadButtons = null;
    private final byte[] gamepadButtonStates = new byte[GLFW.GLFW_GAMEPAD_BUTTON_LAST];

    private boolean useGamepad = true;

    public Controller(int jid) {
        this.jid = jid;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public boolean isPresent() {
        return GLFW.glfwJoystickPresent(jid);
    }

    public boolean isGamepad() {
        return GLFW.glfwJoystickIsGamepad(jid);
    }

    public void updateButtons() {
        ByteBuffer byteBuffer = GLFW.glfwGetJoystickButtons(jid);
        if(byteBuffer != null) {
            buttons = new byte[byteBuffer.remaining()];
            byteBuffer.get(buttons);
        }
    }

    public void updateHats() {
        ByteBuffer byteBuffer = GLFW.glfwGetJoystickHats(jid);
        if(byteBuffer != null) {
            hats = new byte[byteBuffer.remaining()];
            byteBuffer.get(hats);
        }
    }

    public void updateAxes() {
        FloatBuffer floatBuffer = GLFW.glfwGetJoystickAxes(jid);
        if(floatBuffer != null) {
            axes = new float[floatBuffer.remaining()];
            floatBuffer.get(axes);
        }
    }

    public String getJoystickName() {
        return GLFW.glfwGetJoystickName(jid);
    }

    public String getGamepadName() {
        return GLFW.glfwGetGamepadName(jid);
    }

    public void updateGamepad() {
        try(GLFWGamepadState state = GLFWGamepadState.malloc())
        {
            if(GLFW.glfwGetGamepadState(jid, state))
            {
                gamepadAxes = new float[state.axes().remaining()];
                state.axes().get(gamepadAxes);
                gamepadButtons = new byte[state.buttons().remaining()];
                state.buttons().get(gamepadButtons);
            }
        }
    }

    public float[] getAxes() {
        return axes;
    }

    public byte[] getButtons() {
        return buttons;
    }

    public byte[] getHats() {
        return hats;
    }

    public float[] getGamepadAxes() {
        return gamepadAxes;
    }

    public byte[] getGamepadButtons() {
        return gamepadButtons;
    }

    public boolean isUseGamepad() {
        return useGamepad;
    }

    public void setUseGamepad(boolean useGamepad) {
        this.useGamepad = useGamepad;
    }

    @Override
    public void update() {
        if(isUseGamepad()) {
            updateGamepad();
        } else {
            updateAxes();
            updateButtons();
            updateHats();
        }
    }

    public boolean isPressed(Gamepad button) {
        return gamepadButtons[button.getGlfwId()] != 0;
    }

    public boolean isPressedOnce(Gamepad button) {
        boolean pressed = isPressed(button);
        byte state = gamepadButtonStates[button.getGlfwId()];
        if(state == 0){
            //released
            if(pressed) {
                gamepadButtonStates[button.getGlfwId()] = 1; //pressed
                return true;
            }
        } else {
            if(state == 1) {
                //pressed
                if(!pressed) {
                    gamepadButtonStates[button.getGlfwId()] = 0; //released
                }
            }
        }
        return false;
    }

    public float getAxis(Gamepad axis) {
        return gamepadAxes[axis.getGlfwId()];
    }
}

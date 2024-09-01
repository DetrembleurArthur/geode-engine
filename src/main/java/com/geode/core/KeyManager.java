package com.geode.core;

import com.geode.core.key.KeyCommand;
import com.geode.core.key.KeyInput;
import com.geode.core.key.KeyState;
import com.geode.core.key.Keys;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class KeyManager implements Initializable, Closeable {

    private static KeyManager instance;

    private final Window window;
    private final KeyInput[] keys = new KeyInput[GLFW.GLFW_KEY_LAST];
    private boolean enableKeyCommands = true;
    private final List<KeyCommand> keyCommands = new ArrayList<>();

    KeyManager(Window window) throws GeodeException {
        if(instance == null)
            instance = this;
        else
            throw  new GeodeException("KeyManager is a singleton");
        this.window = window;
    }

    public static KeyManager getInstance() {
        return instance;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void init() throws GeodeException {

    }

    public boolean isEnableKeyCommands() {
        return enableKeyCommands;
    }

    public void setEnableKeyCommands(boolean enableKeyCommands) {
        this.enableKeyCommands = enableKeyCommands;
    }

    public KeyManager addCommand(KeyCommand command) {
        keyCommands.add(command);
        return this;
    }

    public void clearCommands() {
        keyCommands.clear();
    }

    public void updateKeyCommands() {
        if(enableKeyCommands) {
            for(KeyCommand command : keyCommands) {
                if(command.isTriggered(this)) {
                    command.run();
                }
            }
        }
    }

    public void updateKey(KeyInput keyInput) {
        keys[keyInput.getScancode()] = keyInput;
        System.err.println(keyInput);
    }

    public KeyInput getKey(Keys key) {
        return keys[getScancode(key)];
    }

    public KeyState getStateKey(Keys key)
    {
        return KeyState.convert(GLFW.glfwGetKey(window.getPointer(), key.getGlfwId()));
    }

    public int getScancode(Keys key) {
        return GLFW.glfwGetKeyScancode(key.getGlfwId());
    }

    public String getChar(Keys key) {
        return GLFW.glfwGetKeyName(key.getGlfwId(), getScancode(key));
    }

    public String getChar(KeyInput input) {
        return GLFW.glfwGetKeyName(input.getKey(), input.getScancode());
    }

    public boolean isKeyPressed(Keys key) {
        return getStateKey(key) == KeyState.PRESSED;
    }

    public void setStickyKeysMode()
    {
        GLFW.glfwSetInputMode(window.getPointer(), GLFW.GLFW_STICKY_KEYS, GLFW.GLFW_TRUE);
    }

    public void setLockKeysMode(boolean state)
    {
        GLFW.glfwSetInputMode(window.getPointer(), GLFW.GLFW_LOCK_KEY_MODS, state ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }
}

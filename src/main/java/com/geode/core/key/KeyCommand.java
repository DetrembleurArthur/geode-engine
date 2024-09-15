package com.geode.core.key;

import com.geode.core.KeyManager;

import java.util.HashSet;
import java.util.Set;

public class KeyCommand implements Runnable {

    private final Set<Keys> keys = new HashSet<>();
    private final Set<KeyMods> mods = new HashSet<>();
    private KeyState state = KeyState.PRESSED;
    private int modsResult = 0;
    private final Runnable runnable;
    private final boolean continuous;
    private boolean hasRun = false;

    public KeyCommand(Runnable runnable, boolean continuous) {
        this.runnable = runnable;
        this.continuous = continuous;
    }

    public KeyCommand key(Keys key) {
        keys.add(key);
        return this;
    }

    public KeyCommand mod(KeyMods mod) {
        mods.add(mod);
        modsResult += mod.getGlfwId();
        return this;
    }

    public KeyCommand setState(KeyState state) {
        this.state = state;
        return this;
    }

    public KeyState getState() {
        return state;
    }

    public boolean isTriggered(KeyManager keyManager) {
        for(Keys key : keys) {
            KeyInput keyInput = keyManager.getKey(key);
            if(keyInput == null || keyInput.getAction() != state.getGlfwId() || keyInput.getMods() != modsResult) {
                hasRun = false;
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        if(runnable != null)
            if(!continuous) {
                if(!hasRun) {
                    runnable.run();
                    hasRun = true;
                }
            } else {
                runnable.run();
            }
    }
}

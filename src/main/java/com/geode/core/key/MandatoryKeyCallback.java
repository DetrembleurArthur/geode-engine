package com.geode.core.key;

import com.geode.core.KeyManager;
import com.geode.core.winevents.WinEvents;

public class MandatoryKeyCallback implements WinEvents.KeyCallback {

    @Override
    public void trigger(KeyInput keyInput) {
        KeyManager.getInstance().updateKey(keyInput);
        KeyManager.getInstance().updateKeyCommands();
    }

    @Override
    public Object tag() {
        return this;
    }
}

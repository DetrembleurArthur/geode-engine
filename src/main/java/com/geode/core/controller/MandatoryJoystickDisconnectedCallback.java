package com.geode.core.controller;

import com.geode.core.ControllerManager;
import com.geode.core.winevents.WinEvents;
import com.geode.core.winevents.WindowCallback;

public class MandatoryJoystickDisconnectedCallback implements WinEvents.JoystickDisconnectedCallback {
    @Override
    public Object tag() {
        return this;
    }

    @Override
    public void trigger(Integer jid) {
        ControllerManager.getInstance().disconnectController(jid);
    }
}

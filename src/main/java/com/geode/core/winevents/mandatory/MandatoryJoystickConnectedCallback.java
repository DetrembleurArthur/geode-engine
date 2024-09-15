package com.geode.core.winevents.mandatory;

import com.geode.core.ControllerManager;
import com.geode.core.winevents.WinEvents;

public class MandatoryJoystickConnectedCallback implements WinEvents.JoystickConnectedCallback {

    @Override
    public Object tag() {
        return this;
    }

    @Override
    public void trigger(Integer jid) {
        ControllerManager.getInstance().connectController(jid);
    }
}

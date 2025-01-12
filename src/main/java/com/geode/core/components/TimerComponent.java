package com.geode.core.components;

import com.geode.core.components.base.UpdateableComponent;
import com.geode.core.time.ActionTimer;
import com.geode.core.time.Timer;
import com.geode.entity.GameObject;
import com.geode.exceptions.GeodeException;
import com.geode.utils.DelayedList;

import java.util.ArrayList;
import java.util.HashMap;

public class TimerComponent extends UpdateableComponent {

    private final DelayedList<ActionTimer> actionTimers = new DelayedList<>();
    private final HashMap<String, Timer> timers = new HashMap<>();

    public TimerComponent(GameObject child) {
        super(child);
    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        for (ActionTimer timer : actionTimers) {
            timer.update();
        }
        actionTimers.applyDelayedActions();
        actionTimers.removeIf(actionTimer -> actionTimer.isCompleted() && !actionTimer.isAutoRestart());
    }

    @Override
    public void close() throws Exception {
        for (ActionTimer timer : actionTimers) {
            timer.stop();
        }
        actionTimers.delayedClear();
    }

    public TimerComponent add(ActionTimer timer) {
        actionTimers.delayedAdd(timer);
        if (timer.getStartCondition() == null) {
            timer.start();
        }
        return this;
    }

    public TimerComponent start(String id, double delaySec) {
        Timer timer = new Timer(delaySec);
        timer.start();
        timers.put(id, timer);
        return this;
    }

    public boolean isCompleted(String id) {
        Timer timer = timers.get(id);
        if (timer != null) {
            boolean result = timer.isCompleted();
            if (result) {
                stop(id);
            }
            return result;
        }
        return false;
    }

    public TimerComponent stop(String id) {
        Timer timer = timers.get(id);
        if (timer != null) {
            timer.stop();
            timers.remove(id);
        }
        return this;
    }
}

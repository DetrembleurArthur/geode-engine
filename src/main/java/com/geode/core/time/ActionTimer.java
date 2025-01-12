package com.geode.core.time;

import com.geode.core.Updateable;
import com.geode.utils.Condition;

public class ActionTimer extends Timer implements Updateable {

    private TimerRunnable onRun;
    private Runnable onStop;
    private Condition startCondition;
    private Condition restartCondition;
    private boolean autoRestart = false;

    public ActionTimer(double sec) {
        super(sec);
    }

    public Condition getStartCondition() {
        return startCondition;
    }

    public ActionTimer setStartCondition(Condition startCondition) {
        this.startCondition = startCondition;
        return this;
    }

    public Condition getRestartCondition() {
        return restartCondition;
    }

    public ActionTimer setRestartCondition(Condition restartCondition) {
        this.restartCondition = restartCondition;
        return this;
    }

    public ActionTimer setStartConditionAsRestartCondition() {
        this.restartCondition = startCondition;
        return this;
    }

    public TimerRunnable getOnRun() {
        return onRun;
    }

    public ActionTimer setOnRun(TimerRunnable onRun) {
        this.onRun = onRun;
        return this;
    }

    public Runnable getOnStop() {
        return onStop;
    }

    public ActionTimer setOnStop(Runnable onStop) {
        this.onStop = onStop;
        return this;
    }

    public boolean isAutoRestart() {
        return autoRestart;
    }

    public ActionTimer setAutoRestart(boolean autoRestart) {
        this.autoRestart = autoRestart;
        return this;
    }

    @Override
    public void update() {
        if (isStarted()) {
            if (isCompleted()) {
                stop();
                if (onStop != null) {
                    onStop.run();
                }
                if (autoRestart) {
                    if (restartCondition == null || restartCondition.ok()) {
                        start();
                    }
                }
            } else {
                if (onRun != null) {
                    onRun.run(getElapsedTime());
                }
            }
        } else if (startCondition != null && startCondition.ok()) {
            start();
            startCondition = null;
        }
    }
}

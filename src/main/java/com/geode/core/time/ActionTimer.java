package com.geode.core.time;

import com.geode.core.Updateable;
import com.geode.utils.Condition;

public class ActionTimer extends Timer implements Updateable {

    private Runnable onRun;
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

    public void setStartCondition(Condition startCondition) {
        this.startCondition = startCondition;
    }

    public Condition getRestartCondition() {
        return restartCondition;
    }

    public void setRestartCondition(Condition restartCondition) {
        this.restartCondition = restartCondition;
    }

    public Runnable getOnRun() {
        return onRun;
    }

    public void setOnRun(Runnable onRun) {
        this.onRun = onRun;
    }

    public Runnable getOnStop() {
        return onStop;
    }

    public void setOnStop(Runnable onStop) {
        this.onStop = onStop;
    }

    public boolean isAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(boolean autoRestart) {
        this.autoRestart = autoRestart;
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
                    onRun.run();
                }
            }
        } else if (startCondition != null && startCondition.ok()) {
            start();
            startCondition = null;
        }
    }
}

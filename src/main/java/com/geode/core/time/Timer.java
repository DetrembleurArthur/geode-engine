package com.geode.core.time;

public class Timer {

    private double startTimestamp = 0;
    private double delay;

    public Timer(double sec) {
        setDelay(sec);
    }

    public void setDelay(double sec) {
        if (sec <= 0) {
            throw new RuntimeException("timer delay must be greater than 0. Got " + sec + " sec");
        }
        delay = sec;
    }

    public void start() {
        if (delay > 0)
            startTimestamp = Time.get_sec();
    }

    public void stop() {
        startTimestamp = 0;
    }

    public double getElapsedTime() {
        return Time.get_sec() - startTimestamp;
    }

    public boolean isCompleted() {
        return getElapsedTime() >= delay;
    }
}

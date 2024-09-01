package com.geode.core.mouse;

public class MouseInput {

    private int button;
    private int action;
    private int mods;

    public MouseInput(int button, int action, int mods) {
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getMods() {
        return mods;
    }

    public void setMods(int mods) {
        this.mods = mods;
    }

    @Override
    public String toString() {
        return "MouseInput{" +
                "button=" + button +
                ", action=" + action +
                ", mods=" + mods +
                '}';
    }
}

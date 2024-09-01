package com.geode.core.key;

public class KeyInput {
    private int key;
    private int scancode;
    private int action;
    private int mods;

    public KeyInput(int key, int scancode, int action, int mods) {
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getScancode() {
        return scancode;
    }

    public void setScancode(int scancode) {
        this.scancode = scancode;
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
        return "KeyInput{" +
                "key=" + key +
                ", scancode=" + scancode +
                ", action=" + action +
                ", mods=" + mods +
                '}';
    }
}

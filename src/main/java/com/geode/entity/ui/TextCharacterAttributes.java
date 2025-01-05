package com.geode.entity.ui;

import org.joml.Vector4f;

public class TextCharacterAttributes {
    private float red = 0f;
    private float green = 0f;
    private float blue = 0f;
    private float alpha = 1f;

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public Vector4f getColor() {
        return new Vector4f(red, green, blue, alpha);
    }
}

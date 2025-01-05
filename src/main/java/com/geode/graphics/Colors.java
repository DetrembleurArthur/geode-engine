package com.geode.graphics;

import org.joml.Vector4f;

public class Colors {

    private static final Vector4f _RED = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
    private static final Vector4f _GREEN = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
    private static final Vector4f _BLUE = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
    private static final Vector4f _YELLOW = new Vector4f(1.0f, 1.0f, 0.0f, 1.0f);
    private static final Vector4f _CYAN = new Vector4f(0.0f, 1.0f, 1.0f, 1.0f);
    private static final Vector4f _MAGENTA = new Vector4f(1.0f, 0.0f, 1.0f, 1.0f);
    private static final Vector4f _WHITE = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    private static final Vector4f _BLACK = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    private static final Vector4f _ORANGE = new Vector4f(1.0f, 0.65f, 0.0f, 1.0f);
    private static final Vector4f _PINK = new Vector4f(1.0f, 0.75f, 0.8f, 1.0f);
    private static final Vector4f _GRAY = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f);
    private static final Vector4f _LIGHT_GRAY = new Vector4f(0.75f, 0.75f, 0.75f, 1.0f);
    private static final Vector4f _DARK_GRAY = new Vector4f(0.25f, 0.25f, 0.25f, 1.0f);
    private static final Vector4f _TRANSPARENT = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);


    public static Vector4f red() {
        return new Vector4f(_RED);
    }

    public static Vector4f green() {
        return new Vector4f(_GREEN);
    }

    public static Vector4f blue() {
        return new Vector4f(_BLUE);
    }

    public static Vector4f yellow() {
        return new Vector4f(_YELLOW);
    }

    public static Vector4f cyan() {
        return new Vector4f(_CYAN);
    }

    public static Vector4f magenta() {
        return new Vector4f(_MAGENTA);
    }

    public static Vector4f white() {
        return new Vector4f(_WHITE);
    }

    public static Vector4f black() {
        return new Vector4f(_BLACK);
    }

    public static Vector4f orange() {
        return new Vector4f(_ORANGE);
    }

    public static Vector4f pink() {
        return new Vector4f(_PINK);
    }

    public static Vector4f gray() {
        return new Vector4f(_GRAY);
    }

    public static Vector4f lightGray() {
        return new Vector4f(_LIGHT_GRAY);
    }

    public static Vector4f darkGray() {
        return new Vector4f(_DARK_GRAY);
    }

    public static Vector4f transparent() {
        return new Vector4f(_TRANSPARENT);
    }

    public static Vector4f rgba(int red, int green, int blue, int alpha) {
        return new Vector4f(red / 255.f, green / 255.f, blue / 255.f, alpha / 255.f);
    }

    public static Vector4f rgb(int red, int green, int blue) {
        return rgba(red, green, blue, 255);
    }
}

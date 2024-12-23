package com.geode.graphics.sprite;

import org.joml.Vector2f;

public class Sprite {
    private Vector2f position = new Vector2f();
    private Vector2f size = new Vector2f();

    public Sprite(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }
}

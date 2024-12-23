package com.geode.graphics.sprite;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class Sprite {
    private Vector2i position = new Vector2i();
    private Vector2f size = new Vector2f();

    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public Vector2f calculateUVsTranslation() {
        return new Vector2f(position.x * size.x, position.y * size.y);
    }
}

package com.geode.entity;

import com.geode.core.Drawable;
import com.geode.core.components.base.Component;
import org.joml.Vector4f;

public class SpacialGameObject extends GameObject implements Drawable {
    private Transform transform = new Transform();
    private Vector4f color = new Vector4f(1, 1, 1, 1);

    public Transform getTransform() {
        return transform;
    }

    public Transform tr() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    @Override
    public void draw() {
        for(Component component : components) {
            if(Drawable.class.isAssignableFrom(component.getClass())) {
                ((Drawable) component).draw();
            }
        }
    }
}

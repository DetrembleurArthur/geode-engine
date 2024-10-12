package com.geode.entity;

public class SpacialGameObject extends GameObject {
    private Transform transform = new Transform();

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}

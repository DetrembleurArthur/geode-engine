package com.geode.entity;

import org.joml.*;
import org.joml.Math;

public class Transform {
    private final Matrix4f model = new Matrix4f();
    private final Matrix4f localModel = new Matrix4f();
    private Matrix4f parentModel = null;
    private boolean dirty = true;
    private Vector3f position = new Vector3f();
    private Vector3f size = new Vector3f(1);
    private Vector3f rotation = new Vector3f();
    private Vector3f origin = new Vector3f();

    public Transform copy(){
        Transform transform = new Transform();
        transform.setPosition(position);
        transform.setSize(size);
        transform.setRotation(rotation);
        transform.setOrigin(origin);
        transform.setParentModel(parentModel);
        return transform;
    }

    public Matrix4f getModel() {
        if (dirty) {
            localModel.identity()
                    .translate(position)
                    .rotate(Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                    .rotate(Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                    .rotate(Math.toRadians(rotation.z), new Vector3f(0, 0, 1))
                    .translate(new Vector3f(origin).negate())
                    .scale(size);
            model.set(localModel);
            dirty = false;
        }
        if (parentModel != null) {
            Vector3f parentScale = new Vector3f();
            parentModel.getScale(parentScale);
            Vector3f inverseParentScale = new Vector3f(
                    1.0f / parentScale.x,
                    1.0f / parentScale.y,
                    1.0f / parentScale.z
            );
            Matrix4f inverseScaleMatrix = new Matrix4f().scaling(inverseParentScale);
            model.set(new Matrix4f(parentModel)
                    .mul(inverseScaleMatrix)
                    .mul(localModel));
        }
        return model;
    }

    public Vector3f getWorldPosition() {
        Vector3f pos = new Vector3f();
        getModel().getTranslation(pos);
        return pos;
    }

    public void getCornersInWorld(Vector2f[] dstWorldPositions) {
        getSubCornersInWorld(new Vector2f(0, 0), new Vector2f(1, 1), dstWorldPositions);
    }

    public void getSubCornersInWorld(Vector2f topLeftNormalized, Vector2f sizeNormalized, Vector2f[] dstWorldPositions) {
        Vector4f localTopLeft = new Vector4f(topLeftNormalized.x, topLeftNormalized.y, 0, 1); // Top-left corner
        Vector4f localTopRight = new Vector4f(topLeftNormalized.x + sizeNormalized.x, topLeftNormalized.y, 0, 1); // Top-right corner
        Vector4f localBottomLeft = new Vector4f(topLeftNormalized.x, topLeftNormalized.y + sizeNormalized.y, 0, 1); // Bottom-left corner
        Vector4f localBottomRight = new Vector4f(topLeftNormalized.x + sizeNormalized.x, topLeftNormalized.y + sizeNormalized.y, 0, 1); // Bottom-right corner

        Vector4f worldTopLeft = new Vector4f();
        Vector4f worldTopRight = new Vector4f();
        Vector4f worldBottomLeft = new Vector4f();
        Vector4f worldBottomRight = new Vector4f();

        Matrix4f modelMatrix = getModel();

        modelMatrix.transform(localTopLeft, worldTopLeft);
        modelMatrix.transform(localTopRight, worldTopRight);
        modelMatrix.transform(localBottomLeft, worldBottomLeft);
        modelMatrix.transform(localBottomRight, worldBottomRight);

        dstWorldPositions[0].set(worldTopLeft.x, worldTopLeft.y);
        dstWorldPositions[1].set(worldTopRight.x, worldTopRight.y);
        dstWorldPositions[2].set(worldBottomLeft.x, worldBottomLeft.y);
        dstWorldPositions[3].set(worldBottomRight.x, worldBottomRight.y);
    }



    public void setParentModel(Matrix4f parentModel) {
        this.parentModel = parentModel;
    }

    public void setSize(Vector3f size) {
        if (size.x <= 0) size.x = 1;
        if (size.y <= 0) size.y = 1;
        if (size.z <= 0) size.z = 1;
        origin.mul(size.x / this.size.x, size.y / this.size.y, size.z / this.size.z);
        this.size = new Vector3f(size);
        dirty = true;
    }

    public void setSize(Vector2i size) {
        if (size.x <= 0) size.x = 1;
        if (size.y <= 0) size.y = 1;
        origin.mul(size.x / this.size.x, size.y / this.size.y, 1);
        this.size = new Vector3f(size.x, size.y, this.size.z);
        dirty = true;
    }

    public void setWidth(float width) {
        setSize(new Vector3f(width, size.y, size.z));
    }

    public void setHeight(float height) {
        setSize(new Vector3f(size.x, height, size.z));
    }

    public void setDepth(float depth) {
        setSize(new Vector3f(size.x, size.y, depth));
    }

    public void setPosition(Vector3f position) {
        this.position = new Vector3f(position);
        dirty = true;
    }

    public void setPosition(Vector2i position) {
        this.position = new Vector3f(position.x, position.y, 0);
        dirty = true;
    }

    public void setPosition(Vector2f position) {
        this.position = new Vector3f(position.x, position.y, 0);
        dirty = true;
    }

    public void setX(float x) {
        setPosition(new Vector3f(x, position.y, position.z));
    }

    public void setY(float y) {
        setPosition(new Vector3f(position.x, y, position.z));
    }

    public void setZ(float z) {
        setPosition(new Vector3f(position.x, position.y, z));
    }

    public void setOrigin(Vector3f origin) {
        this.origin = new Vector3f(origin);
        dirty = true;
    }

    public void setTopLeftOrigin() {
        setOrigin(new Vector3f(0, 0, 0));
    }

    public void setTopRightOrigin() {
        setOrigin(new Vector3f(size.x, 0, 0));
    }

    public void setBottomRightOrigin() {
        setOrigin(new Vector3f(size.x, size.y, 0));
    }

    public void setBottomLeftOrigin() {
        setOrigin(new Vector3f(0, size.y, 0));
    }

    public void setCenterOrigin() {
        setOrigin(new Vector3f(size.x / 2f, size.y / 2f, size.z / 2f));
    }

    public void setOriginPosition(Vector3f targetOrigin, Vector3f position) {
        setPosition(position.add(new Vector3f(this.origin).sub(targetOrigin)));
    }

    public void setTopLeftPosition(Vector3f position) {
        setOriginPosition(new Vector3f(0, 0, 0), position);
    }

    public void setTopRightPosition(Vector3f position) {
        setOriginPosition(new Vector3f(size.x, 0, 0), position);
    }

    public void setBottomLeftPosition(Vector3f position) {
        setOriginPosition(new Vector3f(0, size.y, 0), position);
    }

    public void setBottomRightPosition(Vector3f position) {
        setOriginPosition(new Vector3f(size.x, size.y, 0), position);
    }

    public void setCenterPosition(Vector3f position) {
        setOriginPosition(new Vector3f(size.x / 2f, size.y / 2f, 0), position);
    }

    public Vector3f getOriginPosition(Vector3f targetOrigin) {
        return new Vector3f(position).sub(new Vector3f(origin).sub(targetOrigin));
    }

    public Vector3f getTopLeftPosition() {
        return getOriginPosition(new Vector3f(0, 0, 0));
    }

    public Vector3f getTopRightPosition() {
        return getOriginPosition(new Vector3f(size.x, 0, 0));
    }

    public Vector3f getBottomLeftPosition() {
        return getOriginPosition(new Vector3f(0, size.y, 0));
    }

    public Vector3f getBottomRightPosition() {
        return getOriginPosition(new Vector3f(size.x, size.y, 0));
    }

    public Vector3f getCenterPosition() {
        return getOriginPosition(new Vector3f(size.x / 2f, size.y / 2f, 0));
    }

    public float getAngle2D(Vector3f target) {
        return Math.toDegrees(Math.atan2(-(position.y - target.y), -(position.x - target.x)));
    }

    public Vector3f getDirection(Vector3f targetPosition) {
        return targetPosition.sub(position).normalize();
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        dirty = true;
    }

    public void setRotation2D(float angle) {
        setRotation(new Vector3f(rotation.x, rotation.y, angle));
    }

    // rotation around target by angle degreess (based on the position of the object)
    public void setRelativeAngle2D(Vector3f target, float angle) {
        angle = Math.toRadians(angle);
        Vector3f pos = position.sub(target);
        pos = new Vector3f(
                pos.x * Math.cos(angle) - pos.y * Math.sin(angle),
                pos.x * Math.sin(angle) - pos.y * Math.cos(angle), 0)
                .add(target);
        setPosition(pos);
    }

    // rotation around target by angle degreess at a certain distance (based on the position of the object)
    public void setRelativeAngle2D(Vector3f target, float angle, float distance) {
        angle = Math.toRadians(angle);
        Vector3f pos = position.sub(target).normalize().mul(distance, distance, distance);
        pos = new Vector3f(
                pos.x * Math.cos(angle) - pos.y * Math.sin(angle),
                pos.x * Math.sin(angle) - pos.y * Math.cos(angle), 0)
                .add(target);
        setPosition(pos);
    }

    // set object position at "angle" degrees of the target at "distance" pixels
    public void setAngle2D(Vector3f target, float angle, float distance) {
        angle = Math.toRadians(angle - 45);
        distance = (distance * Math.sqrt(2f)) / 2f;
        Vector3f pos = new Vector3f(distance, distance, 0);
        pos = new Vector3f(
                pos.x * Math.cos(angle) - pos.y * Math.sin(angle),
                pos.x * Math.sin(angle) - pos.y * Math.cos(angle), 0)
                .add(target);
        setPosition(pos);
    }

    public float getDistance(Vector3f target) {
        return getCenterPosition().distance(target);
    }

    public boolean isDirty() {
        return dirty;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getSize() {
        return size;
    }

    public float getWidth() {
        return size.x;
    }

    public float getHeight() {
        return size.y;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public Transform translate(Vector3f translation) {
        setPosition(position.add(translation));
        return this;
    }

    public Transform translateX(float x) {
        setX(position.x + x);
        return this;
    }

    public Transform translateY(float y) {
        setY(position.y + y);
        return this;
    }

    public Transform translateZ(float z) {
        setX(position.z + z);
        return this;
    }

    public Transform rotate(float degree) {
        setRotation2D(rotation.z + degree);
        return this;
    }

    public boolean includes(Vector2f worldPoint) {
        Matrix4f inverseModel = new Matrix4f(getModel()).invert();
        Vector3f localPoint = new Vector3f();
        inverseModel.transformPosition(new Vector3f(worldPoint.x, worldPoint.y, 0), localPoint);
        return localPoint.x >= 0 && localPoint.x <= 1 &&
                localPoint.y >= 0 && localPoint.y <= 1;
    }
}

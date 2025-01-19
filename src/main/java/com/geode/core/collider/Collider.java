package com.geode.core.collider;

import com.geode.core.Closeable;
import com.geode.core.Drawable;
import com.geode.core.Updateable;
import com.geode.entity.Shape;
import com.geode.entity.Transform;
import com.geode.graphics.Colors;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Collider implements Updateable, Drawable, Closeable {
    private final Transform parent;
    private final Transform current = new Transform();
    private final Vector2f[] corners = new Vector2f[4];
    private final Vector2f topLeftNormalized;
    private final Vector2f sizeNormalized;
    private Shape visualShape;

    public Collider(Transform parent, Vector2f topLeftNormalized, Vector2f sizeNormalized) {
        this.parent = parent;
        this.topLeftNormalized = topLeftNormalized;
        this.sizeNormalized = sizeNormalized;
        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Vector2f();
        }
        updateCurrentTransform();
    }

    public Collider(Transform parent) {
        this(parent, new Vector2f(), new Vector2f(1, 1));
    }

    public void enableVisualShape() {
        if (visualShape == null) {
            visualShape = new Shape();
            visualShape.setColor(Colors.rgba(255, 0, 0, 128));
            visualShape.setTransform(current);
        }
    }

    public void disableVisualShape() {
        if (visualShape != null) {
            try {
                visualShape.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            visualShape = null;
        }
    }

    @Override
    public void update() {
        if (parent.isDirty()) {
            updateCurrentTransform();
        }
        current.getCornersInWorld(corners);
    }

    private void updateCurrentTransform() {
        current.setParentModel(parent.getModel());
        current.setX(topLeftNormalized.x * parent.getWidth());
        current.setY(topLeftNormalized.y * parent.getHeight());
        current.setWidth(sizeNormalized.x * parent.getWidth());
        current.setHeight(sizeNormalized.y * parent.getHeight());
    }

    public boolean collide(Vector2f worldPoint) {
        return current.includes(worldPoint);
    }

    public boolean collide(Collider other) {
        for (Vector2f corner : corners) {
            if (other.collide(corner)) {
                return true;
            }
        }
        for (Vector2f corner : other.corners) {
            if (collide(corner)) {
                return true;
            }
        }
        return false;
    }

    public Vector2f[] getCorners() {
        return corners;
    }

    public Transform getParent() {
        return parent;
    }

    public Transform getCurrent() {
        return current;
    }

    public Vector2f getTopLeftNormalized() {
        return topLeftNormalized;
    }

    public Vector2f getSizeNormalized() {
        return sizeNormalized;
    }

    @Override
    public void close() throws Exception {
        if (visualShape != null) {
            visualShape.close();
            visualShape = null;
        }
    }

    @Override
    public void draw() {
        if(visualShape != null) {
            visualShape.draw();
        }
    }
}

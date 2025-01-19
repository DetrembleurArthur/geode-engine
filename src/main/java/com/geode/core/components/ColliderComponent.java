package com.geode.core.components;

import com.geode.core.collider.Collider;
import com.geode.core.components.base.FullStateComponent;
import com.geode.entity.GameObject;
import com.geode.entity.SpacialGameObject;
import com.geode.entity.Transform;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;

import java.util.ArrayList;

public class ColliderComponent extends FullStateComponent {

    private final ArrayList<Collider> colliders = new ArrayList<>();


    public ColliderComponent(GameObject child) {
        super(child);

    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        for (Collider collider : colliders) {
            collider.update();
        }
    }

    @Override
    public void draw() {
        for (Collider collider : colliders)
            collider.draw();
    }


    @Override
    public void close() throws Exception {
        for (Collider collider : colliders)
            collider.close();
    }

    public Collider createCollider(Vector2f topLeftPositionNormalized, Vector2f sizeNormalized) {
        Transform parent = ((SpacialGameObject) child).tr();
        Collider collider = new Collider(parent, topLeftPositionNormalized, sizeNormalized);
        colliders.add(collider);
        return collider;
    }

    public Collider createCollider() {
        return createCollider(new Vector2f(), new Vector2f(1, 1));
    }

    public boolean isCollision(SpacialGameObject other) {
        var otherComponent = other.getComponent(ColliderComponent.class);
        for (Collider collider : colliders) {
            for (Collider otherCollider : otherComponent.colliders) {
                if (collider.collide(otherCollider)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollision(Vector2f worlPosition) {
        for (Collider collider : colliders) {
            if(collider.collide(worlPosition)) {
                return true;
            }
        }
        return false;
    }

    public void showColliders() {
        for (Collider collider : colliders) {
            collider.enableVisualShape();
        }
    }

    public void hideColliders() {
        for (Collider collider : colliders) {
            collider.disableVisualShape();
        }
    }
}

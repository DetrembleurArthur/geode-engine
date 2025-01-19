package com.geode.core.events;

import com.geode.core.MouseManager;
import com.geode.entity.GameObject;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;

public class EventClick extends Event {

    private Vector2f mp;

    public EventClick(GameObject owner, int priority) {
        super(owner, priority);
    }

    public EventClick(GameObject owner) {
        super(owner);
    }

    @Override
    public void init() throws GeodeException {
        getOwner().c_collider().createCollider();
    }

    @Override
    public boolean hasAppend() {
        if(MouseManager.getInstance().isAnyButtonPressed()) {
            mp = MouseManager.getInstance().getFPositionFromCamera2D();
            return getOwner().c_collider().isCollision(mp);
        }
        return false;
    }

    public Vector2f getMp() {
        return mp;
    }
}

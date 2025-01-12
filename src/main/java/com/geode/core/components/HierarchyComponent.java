package com.geode.core.components;

import com.geode.core.Drawable;
import com.geode.core.components.base.FullStateComponent;
import com.geode.entity.GameObject;
import com.geode.entity.SpacialGameObject;
import com.geode.exceptions.GeodeException;
import com.geode.utils.DelayedList;

import java.util.ArrayList;

public class HierarchyComponent extends FullStateComponent {

    private final DelayedList<GameObject> children = new DelayedList<>();

    public HierarchyComponent(GameObject child) {
        super(child);
        setPriority(MAX_PRIORITY);
    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        for (GameObject child : children) {
            child.update();
        }
        children.applyDelayedActions();
    }

    @Override
    public void draw() {
        for (GameObject child : children) {
            if (child instanceof Drawable) {
                ((Drawable) child).draw();
            }
        }
    }

    @Override
    public void close() throws Exception {
        for (GameObject child : children) {
            child.close();
        }
    }

    public HierarchyComponent addChild(GameObject child) {
        children.delayedAdd(child);
        if (child instanceof SpacialGameObject) {
            GameObject owner = getChild();
            if (owner instanceof SpacialGameObject) {
                ((SpacialGameObject) child).tr().setParentModel(((SpacialGameObject) owner).getTransform().getModel());
            }
        }
        child.init();
        return this;
    }

    public HierarchyComponent delChild(GameObject child) {
        children.delayedDel(child);
        try {
            child.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public HierarchyComponent detachChild(GameObject child) {
        children.delayedDel(child);
        if (child instanceof SpacialGameObject) {
            ((SpacialGameObject)child).tr().setParentModel(null);
        }
        return this;
    }
}

package com.geode.core.components.base;

import com.geode.core.Drawable;
import com.geode.entity.GameObject;

public abstract class DrawableComponent extends Component implements Drawable {
    public DrawableComponent(GameObject child) {
        super(child);
    }
}

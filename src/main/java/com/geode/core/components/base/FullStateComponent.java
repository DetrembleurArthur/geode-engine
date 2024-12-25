package com.geode.core.components.base;

import com.geode.core.Drawable;
import com.geode.core.Updateable;
import com.geode.entity.GameObject;

public abstract class FullStateComponent extends Component implements Drawable, Updateable {
    public FullStateComponent(GameObject child) {
        super(child);
    }
}

package com.geode.core.components.base;

import com.geode.entity.GameObject;

public abstract class StaticComponent extends Component {
    public StaticComponent(GameObject child) {
        super(child);
    }
}

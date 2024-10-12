package com.geode.core.components.base;

import com.geode.core.Updateable;
import com.geode.entity.GameObject;

public abstract class UpdateableComponent extends Component implements Updateable {
    public UpdateableComponent(GameObject child) {
        super(child);
    }
}

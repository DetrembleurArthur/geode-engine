package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Model;
import com.geode.graphics.Texture;

public class ModelManager extends ResourceManager<Model> {

    private static ModelManager instance;

    ModelManager() throws GeodeException {
        super("model", Model.class, Extensions.NONE);
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("model manager is a singleton");
    }

    public static ModelManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
    }
}

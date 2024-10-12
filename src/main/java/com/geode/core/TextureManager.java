package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Image;
import com.geode.graphics.Texture;

public class TextureManager extends ResourceManager<Texture> {

    private static TextureManager instance;

    TextureManager() throws GeodeException {
        super("texture", Texture.class, Extensions.TEX_PNG);
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("texture manager is a singleton");
    }

    public static TextureManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
    }
}

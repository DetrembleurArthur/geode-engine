package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import com.geode.graphics.sprite.SpriteSheet;

@Singleton
public class SpriteSheetManager extends ResourceManager<SpriteSheet> {

    private static SpriteSheetManager instance;

    SpriteSheetManager() throws GeodeException {
        super("spritesheet", SpriteSheet.class, Extensions.JSON);
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("spritesheet manager is a singleton");
    }

    public static SpriteSheetManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
    }
}

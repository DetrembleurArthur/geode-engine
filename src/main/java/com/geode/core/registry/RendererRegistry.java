package com.geode.core.registry;

import com.geode.core.Initializable;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.renderer.*;

@Singleton
public class RendererRegistry extends Registry<Renderer<?>> {

    private static RendererRegistry instance;

    public static RendererRegistry getInstance() {
        if (instance == null)
            instance = new RendererRegistry();
        return instance;
    }

    @Override
    public void init() throws GeodeException {
        instance.register(new TextureRenderer())
                .register(new FontRenderer())
                .register(new ShapeRenderer());
    }
}

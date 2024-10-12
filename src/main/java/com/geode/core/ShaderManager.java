package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Shader;
import com.geode.graphics.Texture;

@Singleton
public class ShaderManager extends ResourceManager<Shader> {

    private static ShaderManager instance;

    ShaderManager() throws GeodeException {
        super("shader", Shader.class, Extensions.SHA_GLSL);
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("shader manager is a singleton");
    }

    public static ShaderManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
    }
}

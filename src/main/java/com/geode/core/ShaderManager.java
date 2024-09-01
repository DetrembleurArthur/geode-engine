package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Shader;

@Singleton
public class ShaderManager extends ResourceManager<Shader> {

    private static ShaderManager instance;

    ShaderManager() throws GeodeException {
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("shader manager is a singleton");
    }

    public static ShaderManager getInstance() {
        return instance;
    }

    @Override
    public Shader addResource(String name, String subPath, Extensions ext, ResourceLocator locator) throws GeodeException {
        if(resources.containsKey(name))
            return getResource(name);
        if(!subPath.isEmpty() && !subPath.endsWith("/"))
            subPath += "/";
        String extention = ext.toString();
        if(ext == Extensions.NONE)
            extention = Extensions.SHA_GLSL.toString();
        String location = locator.getLocation(Shader.class) + subPath;
        return addResource(name, new Shader(location + "vertex_" + name + "." + ext, location + "fragment_" + name + "." + ext));
    }

    @Override
    public void init() throws GeodeException {
    }
}

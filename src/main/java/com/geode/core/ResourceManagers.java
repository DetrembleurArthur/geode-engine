package com.geode.core;

import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Shader;
import com.geode.graphics.Texture;

import java.util.HashMap;

@Singleton
public class ResourceManagers implements Initializable, Closeable {

    private static ResourceManagers instance;

    private final HashMap<Class<? extends Resource>, ResourceManager<? extends Resource>> resourceManagers = new HashMap<>();

    ResourceManagers() throws GeodeException {
        if(instance == null) {
            instance = this;
        }
        else {
            throw new GeodeException("resources manager is a singleton");
        }
    }

    public <T extends Resource> void register(Class<T> resourceClass, ResourceManager<T> manager) throws GeodeException {
        resourceManagers.putIfAbsent(resourceClass, manager);
        manager.init();
    }

    public <T extends Resource> ResourceManager<T> get(Class<T> resourceClass) {
        return (ResourceManager<T>) resourceManagers.get(resourceClass);
    }

    public static ResourceManagers getInstance() {
        return instance;
    }

    @Override
    public void close() throws Exception {
        resourceManagers.forEach((aClass, resourceManager) -> {
            try {
                resourceManager.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void init() throws GeodeException {
        register(Shader.class, new ShaderManager());
        register(Texture.class, new TextureManager());
        register(Settings.class, new SettingsManager());
    }
}

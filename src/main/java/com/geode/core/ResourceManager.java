package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.exceptions.GeodeException;

import java.util.HashMap;

public abstract class ResourceManager<T extends Resource> implements Initializable, Closeable {

    protected final HashMap<String, T> resources = new HashMap<>();

    public abstract T addResource(String name, String subPath, Extensions ext, ResourceLocator locator) throws GeodeException;

    public T addResource(String name, ResourceLocator locator) throws GeodeException {
        return addResource(name, "", Extensions.NONE, locator);
    }

    public T addResource(String name, T resource) throws GeodeException {
        if(!resources.containsKey(name)) {
            resource.init();
            resources.put(name, resource);
        }
        return resource;
    }

    public void removeResource(String name) {
        resources.remove(name);
    }

    public T getResource(String name) {
        return resources.get(name);
    }

    @Override
    public void close() throws Exception {
        resources.forEach((s, resource) -> {
            try {
                resource.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

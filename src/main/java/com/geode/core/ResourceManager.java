package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Image;
import com.geode.graphics.Texture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class ResourceManager<T extends Resource> implements Initializable, Closeable {

    private static final Logger logger = LogManager.getLogger(ResourceManager.class);

    protected final HashMap<String, T> resources = new HashMap<>();
    private final String managerId;
    private final Class<T> clazz;
    private final Extensions defaultExt;

    protected ResourceManager(String managerId, Class<T> clazz, Extensions defaultExt) {
        this.managerId = managerId;
        this.clazz = clazz;
        this.defaultExt = defaultExt;
    }

    public T addResource(String name, String subPath, Extensions ext, ResourceLocator locator) throws GeodeException {
        String fullName = subPath + "." + name;
        if(resources.containsKey(fullName))
            return getResource(fullName);
        if(!subPath.isEmpty() && !subPath.endsWith("/"))
            subPath += "/";
        String extention = ext.toString();
        if(ext == Extensions.NONE)
            extention = defaultExt.toString();
        String location = locator.getLocation(clazz) + subPath + managerId + "_" + name + "." + extention;
        try {
            T resource = clazz.getConstructor(String.class).newInstance(location);
            return addResource(fullName, resource);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new GeodeException(e);
        }
    }

    public T addResource(String name, ResourceLocator locator) throws GeodeException {
        return addResource(name, "", Extensions.NONE, locator);
    }

    public T addResource(String name, T resource) throws GeodeException {
        if(!resources.containsKey(name)) {
            logger.info("add Resource<{}> : {}", resource.getClass().getName(), name);
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
                if(resource.isLoaded()) {
                    logger.info("close Resource<" + resource.getClass().getName() + "> : " + s);
                    resource.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

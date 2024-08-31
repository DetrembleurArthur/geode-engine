package com.geode.core.reflections;

import com.geode.exceptions.GeodeException;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PackageClassLoaderManager {

    public enum Defaults {
        SCENES,
        EVENTS
    }

    private final HashMap<String, PackageClassLoader> loaders = new HashMap<>();

    public void register(String id, String packagePath, Class<?> targetClass, Class<? extends Annotation> targetAnnotationClass) throws GeodeException {
        PackageClassLoader loader = new PackageClassLoader(packagePath, targetClass, targetAnnotationClass);
        loader.init();
        loaders.putIfAbsent(id, loader);
    }

    public void register(Enum<?> id, String packagePath, Class<?> targetClass, Class<? extends Annotation> targetAnnotationClass) throws GeodeException {
        register(id.name(), packagePath, targetClass, targetAnnotationClass);
    }

    public Set<Class<?>> get(String id) {
        PackageClassLoader loader = loaders.get(id);
        if(loader != null) {
            return loader.getLoadedClasses();
        }
        return new HashSet<>();
    }

    public Set<Class<?>> get(Enum<?> id) {
        return get(id.name());
    }
}

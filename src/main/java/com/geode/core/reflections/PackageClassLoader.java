package com.geode.core.reflections;

import com.geode.core.Application;
import com.geode.core.Initializable;
import com.geode.core.Scene;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class PackageClassLoader implements Initializable {

    private static final Logger logger = LogManager.getLogger(PackageClassLoader.class);

    private final Reflections reflections;
    private final String packagePath;
    private final Class<?> targetClass;
    private final Class<? extends Annotation> targetAnnotationClass;
    private Set<Class<?>> loadedClasses = new HashSet<>();

    PackageClassLoader(String packagePath, Class<?> targetClass, Class<? extends Annotation> targetAnnotationClass) {
        this.packagePath = packagePath;
        this.targetClass = targetClass;
        this.targetAnnotationClass = targetAnnotationClass;
        reflections = new Reflections(packagePath);
    }

    public String getPackagePath() {
        return packagePath;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<? extends Annotation> getTargetAnnotationClass() {
        return targetAnnotationClass;
    }

    public Set<Class<?>> getLoadedClasses() {
        return loadedClasses;
    }

    @Override
    public void init() throws GeodeException {
        loadedClasses = reflections.get(Scanners.SubTypes
                .of(targetClass)
                .asClass()
                .filter(aClass -> aClass.isAnnotationPresent(targetAnnotationClass)));
        for(Class<?> classes : loadedClasses) {
            logger.info("class " + classes.getName() + " loaded ");
        }
    }
}

package com.geode.core;

import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
public class ResourceLocator {

    private static final Logger logger = LogManager.getLogger(ResourceLocator.class);

    private static ResourceLocator instance;

    private String resourceFolder = "";
    private final HashMap<Class<? extends Resource>, String> locations = new HashMap<>();

    ResourceLocator() throws GeodeException {
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("resource locator is a singleton");
    }

    public void setResourceFolder(String folder) {
        if(!folder.endsWith("/"))
            folder += "/";
        resourceFolder = folder;
    }

    public String getResourceFolder() {
        return resourceFolder;
    }

    public void setLocation(Class<? extends Resource> resourceClass, String location) {
        if(!location.endsWith("/"))
            location += "/";
        locations.put(resourceClass, resourceFolder + location);
        logger.info("{} can be found at {}", resourceClass.getName(), getLocation(resourceClass));
    }

    public String getLocation(Class<? extends Resource> resourceClass) {
        return locations.get(resourceClass);
    }

    public List<File> getFiles(Class<? extends Resource> resourceClass) {
        String location = getLocation(resourceClass);
        List<File> locations = new ArrayList<>();
        if(location != null) {
            File folder = new File(location);
            if(folder.isDirectory()) {
                File[] files = folder.listFiles();
                if(files != null) {
                    for(File file : files) {
                        if(file.isFile()) {
                            locations.add(file);
                        }
                    }
                }
            }
        }
        return locations;
    }

    public static ResourceLocator getInstance() {
        return instance;
    }

    public static final String SHADERS = "src/main/resources/shaders";
    public static final String TEXTURES = "src/main/resources/textures";
    public static final String FONTS = "src/main/resources/fonts";
    public static final String SOUNDS = "src/main/resources/sounds";
}

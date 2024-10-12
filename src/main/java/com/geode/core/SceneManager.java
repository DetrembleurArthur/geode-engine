package com.geode.core;

import com.geode.core.reflections.*;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Singleton
public class SceneManager implements Initializable, Closeable {

    private static final Logger logger = LogManager.getLogger(SceneManager.class);
    private static SceneManager instance;
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private Scene currentScene;
    private final Set<Class<?>> sceneClasses;

    SceneManager(Set<Class<?>> classes) throws GeodeException {
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("SceneManager is a singleton");
        sceneClasses = classes;
    }

    public static SceneManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
        logger.info("initialize SceneManager");
        sceneClasses.stream().filter(cl -> Scene.class.isAssignableFrom(cl) && cl.isAnnotationPresent(SceneEntry.class))
                .forEach(cl -> {
                    SceneEntry sceneEntry = cl.getAnnotation(SceneEntry.class);
                    try {
                        if(!scenes.containsKey(sceneEntry.value())) {
                            Scene scene = (Scene) cl.getConstructor().newInstance();
                            injectFields(scene);
                            scene.init();
                            scenes.put(sceneEntry.value(), scene);
                            if(sceneEntry.first() && currentScene == null) {
                                currentScene = scene;
                                logger.info("Scene {} set as current scene", sceneEntry.value());
                            }
                            logger.info("register {} Scene", sceneEntry.value());
                        } else {
                            logger.warn("Scene {} already exists => ignore", sceneEntry.value());
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
        if(currentScene != null) {
            currentScene.select();
        }
        sceneClasses.clear();
        logger.info("SceneManager initialize");
    }

    private void injectFields(Scene scene) {
        List<Field> injectables = FieldSearcher.getFields(Inject.class, scene);
        injectables.forEach(field -> {
            try {
                if(field.getType() == Application.class) {
                    field.set(scene, Application.getInstance());
                } else if(field.getType().isAnnotationPresent(Singleton.class)) {
                    Method method = field.getType().getMethod("getInstance");
                    if(Modifier.isStatic(method.getModifiers())) {
                        field.set(scene, method.invoke(null));
                    }
                } else if(field.getType().isAnnotationPresent(ResourceHolder.class)) {
                    ResourceHolder resourceHolder = field.getType().getAnnotation(ResourceHolder.class);
                    Object instance = field.getType().getConstructor().newInstance();
                    List<Field> artifacts = FieldSearcher.getFields(Artifact.class, instance);
                    artifacts.forEach(artifact -> {
                        Artifact annotation = artifact.getAnnotation(Artifact.class);
                        if(Resource.class.isAssignableFrom(artifact.getType())) {
                            Class<? extends Resource> resourceClass = (Class<? extends Resource>) artifact.getType();
                            ResourceManager<? extends Resource> manager = ResourceManagers.getInstance().get(resourceClass);
                            try {
                                Resource resource = manager.getResource(resourceHolder.value() + "." + annotation.value());
                                if(resource == null)
                                    resource = manager.addResource(annotation.value(), resourceHolder.value(), annotation.ext(), ResourceLocator.getInstance());

                                artifact.set(instance, resource);
                            } catch (GeodeException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    field.set(scene, instance);
                }else {
                    field.set(scene, field.getType().getConstructor().newInstance());
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void close() throws Exception {
        scenes.forEach((s, scene) -> {
            try {
                logger.info("close Scene {}", s);
                scene.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setCurrent(String id) throws GeodeException {
        Scene scene = scenes.get(id);
        if(scene != null) {
            if(scene != currentScene) {
                currentScene.unselect();
                currentScene = scene;
                currentScene.select();
                logger.info("Scene {} set as current", id);
            }
        }
    }

    public Scene getCurrent() {
        return currentScene;
    }
}

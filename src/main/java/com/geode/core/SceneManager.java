package com.geode.core;

import com.geode.core.reflections.FieldSearcher;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.reflections.Singleton;
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

public class SceneManager implements Initializable, AutoCloseable {

    private static final Logger logger = LogManager.getLogger(SceneManager.class);
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private Scene currentScene;
    private final Set<Class<?>> sceneClasses;

    public SceneManager(Set<Class<?>> classes) {
        sceneClasses = classes;
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
                            List<Field> injectables = FieldSearcher.getFields(Inject.class, scene);
                            injectables.forEach(field -> {
                                try {
                                    if(field.getType() == Application.class) {
                                        field.set(scene, Application.getInstance());
                                    } else if(field.getType() == SceneManager.class) {
                                        field.set(scene, this);
                                    } else if(field.getType() == WindowManager.class) {
                                        field.set(scene, Application.getInstance().getWindowManager());
                                    } else if(field.getType() == WindowEventsManager.class) {
                                        field.set(scene, Application.getInstance().getWindowManager().getWindowEventsManager());
                                    }else if(field.getType().isAnnotationPresent(Singleton.class)) {
                                        Method method = field.getType().getMethod("getInstance");
                                        if(Modifier.isStatic(method.getModifiers())) {
                                            field.set(scene, method.invoke(null));
                                        }
                                    } else {
                                        field.set(scene, field.getType().getConstructor().newInstance());
                                    }
                                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                                         InstantiationException e) {
                                    throw new RuntimeException(e);
                                }
                            });
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
        sceneClasses.clear();
        logger.info("SceneManager initialize");
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

    public void setCurrent(String id) {
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

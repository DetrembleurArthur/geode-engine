package com.geode.entity;

import com.geode.core.Updateable;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GameObjectLayer implements AutoCloseable, Updateable {

    private static final Logger logger = LogManager.getLogger(GameObjectLayer.class);
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<SpacialGameObject> spatialGameObjects = new ArrayList<>();

    @Override
    public void update() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        for (GameObject spatialGameObject : spatialGameObjects) {
            spatialGameObject.update();
        }
    }

    public void draw() {
        for (SpacialGameObject spatialGameObject : spatialGameObjects) {
            spatialGameObject.draw();
        }
    }

    public GameObjectLayer add(GameObject gameObject) {
        if (gameObject instanceof SpacialGameObject) {
            spatialGameObjects.add((SpacialGameObject) gameObject);
        } else {
            gameObjects.add(gameObject);
        }
        try {
            gameObject.init();
        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public GameObjectLayer del(GameObject gameObject) {
        if (gameObject instanceof SpacialGameObject) {
            spatialGameObjects.remove(gameObject);
        } else {
            gameObjects.remove(gameObject);
        }
        try {
            gameObject.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public void close() throws Exception {
        for (GameObject gameObject : gameObjects) {
            gameObject.close();
        }
        for (SpacialGameObject gameObject : spatialGameObjects) {
            gameObject.close();
        }
        logger.info("clear {} game objects", (gameObjects.size() + spatialGameObjects.size()));
        gameObjects.clear();
        spatialGameObjects.clear();
    }

    public ArrayList<SpacialGameObject> getSpatialGameObjects() {
        return spatialGameObjects;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}

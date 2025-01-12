package com.geode.entity;

import com.geode.core.Updateable;
import com.geode.exceptions.GeodeException;
import com.geode.utils.DelayedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GameObjectLayer implements AutoCloseable, Updateable {

    private static final Logger logger = LogManager.getLogger(GameObjectLayer.class);
    private final DelayedList<GameObject> gameObjects = new DelayedList<>();
    private final DelayedList<SpacialGameObject> spatialGameObjects = new DelayedList<>();

    @Override
    public void update() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        for (GameObject spatialGameObject : spatialGameObjects) {
            spatialGameObject.update();
        }
        gameObjects.applyDelayedActions();
        spatialGameObjects.applyDelayedActions();
    }

    public void draw() {
        for (SpacialGameObject spatialGameObject : spatialGameObjects) {
            spatialGameObject.draw();
        }
    }

    public GameObjectLayer add(GameObject gameObject) {
        if (gameObject instanceof SpacialGameObject) {
            spatialGameObjects.delayedAdd((SpacialGameObject) gameObject);
        } else {
            gameObjects.delayedAdd(gameObject);
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
            spatialGameObjects.delayedDel((SpacialGameObject) gameObject);
        } else {
            gameObjects.delayedDel(gameObject);
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
        gameObjects.delayedClear();
        spatialGameObjects.delayedClear();
    }

    public ArrayList<SpacialGameObject> getSpatialGameObjects() {
        return spatialGameObjects;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}

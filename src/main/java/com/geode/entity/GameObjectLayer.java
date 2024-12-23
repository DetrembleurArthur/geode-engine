package com.geode.entity;

import com.geode.core.Updateable;
import com.geode.core.components.render.RendererComponent;
import com.geode.exceptions.GeodeException;

import java.util.ArrayList;

public class GameObjectLayer implements AutoCloseable, Updateable {

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<SpacialGameObject> spatialGameObjects = new ArrayList<>();
    private final ArrayList<RendererComponent> renderersCache = new ArrayList<>();

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
        for (RendererComponent component : renderersCache) {
            component.render();
        }
    }

    public GameObjectLayer add(GameObject gameObject) {
        if (gameObject instanceof SpacialGameObject) {
            try {
                renderersCache.add(gameObject.getComponent(RendererComponent.class));
                spatialGameObjects.add((SpacialGameObject) gameObject);
            } catch (GeodeException e) {
                throw new RuntimeException(e);
            }
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
            try {
                renderersCache.remove(gameObject.getComponent(RendererComponent.class));
                spatialGameObjects.remove(gameObject);
            } catch (GeodeException e) {
                throw new RuntimeException(e);
            }
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
        gameObjects.clear();
        renderersCache.clear();
    }
}

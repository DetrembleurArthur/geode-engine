package com.geode.entity;

import com.geode.core.Initializable;
import com.geode.core.SceneManager;
import com.geode.core.Updateable;
import com.geode.exceptions.GeodeException;
import com.geode.utils.DelayedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GameObjectManager implements Initializable, AutoCloseable, Updateable {

    private static final Logger logger = LogManager.getLogger(GameObjectManager.class);
    private final DelayedList<GameObjectLayer> layers = new DelayedList<>();


    @Override
    public void init() throws GeodeException {
        logger.info("initialized");
        layers.add(new GameObjectLayer());
    }

    @Override
    public void update() {
        for (GameObjectLayer layer : layers) {
            layer.update();
        }
        layers.applyDelayedActions();
    }

    public void initDelayedActions() {
        layers.applyDelayedActions();
        for(GameObjectLayer layer : layers) {
            layer.initDelayedActions();
        }
    }

    public void draw() {
        for (GameObjectLayer layer : layers) {
            layer.draw();
        }
    }

    public GameObjectLayer layer(int layerId) {
        return layers.get(layerId);
    }

    public GameObjectLayer layer() {
        return layers.getFirst();
    }

    public GameObjectLayer createLayer() {
        GameObjectLayer layer = new GameObjectLayer();
        layers.delayedAdd(layer);
        return layer;
    }

    public ArrayList<GameObjectLayer> getLayers() {
        return layers;
    }

    @Override
    public void close() throws Exception {
        for (GameObjectLayer layer : layers) {
            layer.close();
        }
        logger.info("closed");
    }
}

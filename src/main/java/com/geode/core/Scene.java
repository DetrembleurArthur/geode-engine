package com.geode.core;

import com.geode.entity.GameObject;
import com.geode.entity.GameObjectLayer;
import com.geode.entity.GameObjectManager;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera2D;

public abstract class Scene implements Initializable, Closeable, Taggable {

    protected final GameObjectManager goManager = new GameObjectManager();
    protected Camera2D defaultCamera;

    public final void innerInit() throws GeodeException {
        defaultCamera = new Camera2D(WindowManager.getInstance());
        goManager.init();
    }

    public final void innerClose() throws Exception {
        goManager.close();
    }

    // appelée à l'instanciation
    public abstract void init();

    // appelée lors d'une dé-pause
    public void resume() throws GeodeException {

    }

    // appelée lors d'une pause
    public void pause() throws GeodeException {

    }

    // appelée lorsque la scene est mise en "courante"
    public void select() throws GeodeException {

    }

    // appelée lorsque la scene n'est plus courante
    public void unselect() throws GeodeException {

    }

    public abstract void update(double dt);
    public void innerUpdate() {
        goManager.update();
    }

    public void draw(double dt) throws GeodeException {
        goManager.draw();
    }

    @Override
    public Object tag() {
        return this;
    }
}

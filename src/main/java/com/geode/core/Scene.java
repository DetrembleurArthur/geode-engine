package com.geode.core;

public abstract class Scene implements Initializable, Closeable {

    // appelée à l'instanciation
    public abstract void init();

    // appelée à la première utilisation
    public abstract void initResources();

    // appelée lors d'une dé-pause
    public void resume() {

    }

    // appelée lors d'une pause
    public void pause() {

    }

    // appelée lorsque la scene est mise en "courante"
    public void select() {

    }

    // appelée lorsque la scene n'est plus courante
    public void unselect() {

    }

    public abstract void update(double dt);
    public abstract void draw(double dt);
}

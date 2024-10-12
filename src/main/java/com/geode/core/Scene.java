package com.geode.core;

import com.geode.exceptions.GeodeException;

public abstract class Scene implements Initializable, Closeable, Taggable {

    // appelée à l'instanciation
    public abstract void init();

    // appelée lors d'une dé-pause
    public void resume() throws GeodeException {

    }

    // appelée lors d'une pause
    public void pause() throws GeodeException  {

    }

    // appelée lorsque la scene est mise en "courante"
    public void select() throws GeodeException  {

    }

    // appelée lorsque la scene n'est plus courante
    public void unselect() throws GeodeException  {

    }

    public abstract void update(double dt);
    public abstract void draw(double dt) throws GeodeException;

    @Override
    public Object tag() {
        return this;
    }
}

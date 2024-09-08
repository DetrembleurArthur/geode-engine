package com.geode.core;

import com.geode.exceptions.GeodeException;

public interface Resource extends Initializable, Closeable {
    default void load() throws GeodeException {
        if(!isLoaded())
            init();
    }
    boolean isLoaded();
}

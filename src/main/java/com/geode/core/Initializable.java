package com.geode.core;

import com.geode.exceptions.GeodeException;

public interface Initializable {
    void init() throws GeodeException;
}

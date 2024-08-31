package com.geode.core.winevents;

public interface WindowCallback {
    default Object tag() {
        return null;
    }
}

package com.geode.core;

public interface Taggable {
    default Object tag() {
        return null;
    }
}

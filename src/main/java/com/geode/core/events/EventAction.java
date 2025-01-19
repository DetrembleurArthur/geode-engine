package com.geode.core.events;

public interface EventAction<T extends Event> {
    void action(T sourceEvent);
}

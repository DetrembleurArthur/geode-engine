package com.geode.core.events;

import com.geode.core.Initializable;
import com.geode.entity.GameObject;

import java.util.Objects;

public abstract class Event implements Initializable, Comparable<Event> {

    private final GameObject owner;
    private final int priority;

    public Event(GameObject owner, int priority) {
        this.owner = owner;
        this.priority = priority;
    }

    public Event(GameObject owner) {
        this(owner, 0);
    }

    public abstract boolean hasAppend();

    public int getPriority() {
        return priority;
    }

    public GameObject getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    @Override
    public int compareTo(Event other) {
        return Integer.compare(priority, other.getPriority());
    }

    public <T extends Event> T as() {
        return (T)this;
    }
}

package com.geode.core.components;

import com.geode.core.components.base.UpdateableComponent;
import com.geode.core.events.Event;
import com.geode.core.events.EventAction;
import com.geode.entity.GameObject;
import com.geode.exceptions.GeodeException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EventComponent extends UpdateableComponent {

    private final Set<Event> events = new TreeSet<>();
    private final HashMap<Class<? extends Event>, EventAction<Event>> actions = new HashMap<>();

    public EventComponent(GameObject child) {
        super(child);
    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        for (Event event : events) {
            if(event.hasAppend()) {
                var action = actions.get(event.getClass());
                if(action != null) {
                    action.action(event);
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        events.clear();
        actions.clear();
    }


    public EventComponent registerEvent(Class<? extends Event> clazz) {
        try {
            Event event = clazz.getConstructor(GameObject.class).newInstance(child);
            events.add(event);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public EventComponent setAction(Class<? extends Event> clazz, EventAction<Event> action) {
        actions.put(clazz, action);
        return this;
    }
}

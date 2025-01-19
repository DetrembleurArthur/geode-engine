package com.geode.entity;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.core.Updateable;
import com.geode.core.components.*;
import com.geode.core.components.base.Component;
import com.geode.exceptions.GeodeException;
import com.geode.utils.DelayedList;

import java.util.ArrayList;
import java.util.Comparator;

public class GameObject implements Initializable, Updateable, Closeable {
    protected final DelayedList<Component> components = new DelayedList<>();

    public ArrayList<Component> getComponents() {
        return components;
    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        for (Component component : components) {
            if (Updateable.class.isAssignableFrom(component.getClass())) {
                ((Updateable) component).update();
            }
        }
        components.applyDelayedActions();
    }

    @Override
    public void close() throws Exception {
        for (Component component : components) {
            component.close();
        }
        components.delayedClear();
    }

    public <T extends Component> boolean hasComponent(Class<T> clazz) {
        return components.stream().anyMatch(component -> component.getClass().equals(clazz));
    }


    public <T extends Component> T getComponent(Class<T> clazz) throws GeodeException {
        for (Component component : components) {
            if (component.getClass().equals(clazz)) {
                return (T) component;
            }
        }
        try {
            T target = clazz.getConstructor(GameObject.class).newInstance(this);
            components.add(target);
            sortComponents();
            target.init();
            return target;
        } catch (Exception e) {
            throw new GeodeException(e);
        }
    }

    public <T extends Component> void removeComponent(Class<T> clazz) throws Exception {
        Component target = null;
        for (Component component : components) {
            if (component.getClass().equals(clazz)) {
                target = component;
                break;
            }
        }
        if (target != null) {
            components.delayedDel(target);
            target.close();
        }
    }

    public void removeComponent(Object tag) throws Exception {
        ArrayList<Component> targets = new ArrayList<>();
        for (Component component : components) {
            if (component.tag() == tag) {
                targets.add(component);
                component.close();
            }
        }
        components.removeAll(targets);
    }

    public void sortComponents() {
        components.sort(Comparator.comparingInt(Component::getPriority));
    }

    public ColliderComponent c_collider() {
        return getComponent(ColliderComponent.class);
    }

    public HierarchyComponent c_hierarchy() {
        return getComponent(HierarchyComponent.class);
    }

    public LambdaComponent c_lambda() {
        return getComponent(LambdaComponent.class);
    }

    public RendererComponent c_renderer() {
        return getComponent(RendererComponent.class);
    }

    public SpriteComponent c_sprite() {
        return getComponent(SpriteComponent.class);
    }

    public TimerComponent c_timer() {
        return getComponent(TimerComponent.class);
    }

    public EventComponent c_event() {
        return getComponent(EventComponent.class);
    }
}

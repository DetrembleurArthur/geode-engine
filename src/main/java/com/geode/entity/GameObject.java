package com.geode.entity;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.core.Updateable;
import com.geode.core.components.base.Component;
import com.geode.core.components.base.UpdateableComponent;
import com.geode.exceptions.GeodeException;

import java.util.ArrayList;

public class GameObject implements Initializable, Updateable, Closeable {
    protected final ArrayList<Component> components = new ArrayList<>();

    public ArrayList<Component> getComponents() {
        return components;
    }

    @Override
    public void init() throws GeodeException {

    }

    @Override
    public void update() {
        for(Component component : components) {
            if(component instanceof UpdateableComponent) {
                ((UpdateableComponent) component).update();
            }
        }
    }

    @Override
    public void close() throws Exception {
        for(Component component : components) {
            component.close();
        }
        components.clear();
    }

    public <T extends Component> T getComponent(Class<T> clazz) throws GeodeException {
        for(Component component : components) {
            if(component.getClass().equals(clazz)) {
                return (T) component;
            }
        }
        try {
            T target = clazz.getConstructor(GameObject.class).newInstance(this);
            target.init();
            components.add(target);
            return target;
        } catch (Exception e) {
            throw new GeodeException(e);
        }
    }

    public <T extends Component> void removeComponent(Class<T> clazz) throws Exception {
        Component target = null;
        for(Component component : components) {
            if(component.getClass().equals(clazz)) {
                target = component;
                break;
            }
        }
        if(target != null) {
            components.remove(target);
            target.close();
        }
    }

    public void removeComponent(Object tag) throws Exception {
        ArrayList<Component> targets = new ArrayList<>();
        for(Component component : components) {
            if(component.tag() == tag) {
                targets.add(component);
                component.close();
            }
        }
        components.removeAll(targets);
    }
}

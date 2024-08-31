package com.geode.core.winevents;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.core.WindowEventsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class WindowCallbacksHandler implements Initializable, Closeable {

    private static final Logger logger = LogManager.getLogger(WindowCallbacksHandler.class);

    private final List<WindowCallback> callbacks = new ArrayList<>();

    protected final WindowEventsManager windowEventManager;

    private int maxCallbacks = Integer.MAX_VALUE;
    private boolean concurrentProof = true;
    private boolean locked = false;

    WindowCallbacksHandler(WindowEventsManager windowEventManager) {
        this.windowEventManager = windowEventManager;
    }

    public void addCallback(WindowCallback callback) {
        if(!this.locked) {
            if(callbacks.size() >= maxCallbacks) {
                logger.warn("cannot add callbacks to the event because of max limit reached: {}", maxCallbacks);
            } else {
                callbacks.add(callback);
                logger.info("callbacks: {}/{}", callbacks.size(), maxCallbacks);
            }
        } else {
            logger.warn("this event has locked its callbacks");
        }
    }

    public void removeCallback(WindowCallback callback) {
        if(!this.locked) {
            callbacks.remove(callback);
        } else {
            logger.warn("this event has locked its callbacks");
        }
    }

    public void removeByTag(Object tag) {
        if(!this.locked) {
            List<WindowCallback> callbackList = callbacks.stream()
                    .filter(callback -> callback.tag() == tag)
                    .collect(Collectors.toList());
            if(!callbackList.isEmpty()) {
                logger.info("remove {} callbacks", callbackList.size());
                callbacks.removeAll(callbackList);
            }
        } else {
            logger.warn("this event has locked its callbacks");
        }
    }

    protected void trigger(Class<? extends WindowCallback> callbackClass, Object ... args) {
        List<WindowCallback> snapshot = (concurrentProof && !locked ) ? new ArrayList<>(callbacks) : callbacks;
        snapshot.stream().filter(callback -> callbackClass.isAssignableFrom(callback.getClass())).forEach(callback -> {
            try {
                Method method = callback.getClass().getMethod("trigger", Arrays.stream(args)
                        .map(Object::getClass)
                        .toArray(Class<?>[]::new));
                method.setAccessible(true);
                method.invoke(callback, args);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void clear() {
        callbacks.clear();
    }

    public int getMaxCallbacks() {
        return maxCallbacks;
    }

    public void setMaxCallbacks(int maxCallbacks) {
        if(maxCallbacks <= 0) {
            logger.info("max callbacks must be greather than 0. Provide {}", maxCallbacks);
            return;
        }
        if(this.maxCallbacks > 1)
            this.maxCallbacks = maxCallbacks;
        else
            logger.warn("callback on this event is unique");
    }

    public void uniqueCallback() {
        setMaxCallbacks(1);
    }

    public boolean isConcurrentProof() {
        return concurrentProof;
    }

    public void setConcurrentProof(boolean concurrentProof) {
        this.concurrentProof = concurrentProof;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if(!this.locked)
            this.locked = locked;
        else
            logger.warn("this event has locked its callbacks");
    }
}

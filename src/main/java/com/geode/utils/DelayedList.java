package com.geode.utils;

import java.util.ArrayList;

public class DelayedList<T> extends ArrayList<T> {
    private static class DelayedAction<T> {
        private final DelayedActionType actionType;
        private final T data;

        private DelayedAction(DelayedActionType actionType, T data) {
            this.actionType = actionType;
            this.data = data;
        }
    }

    private enum DelayedActionType {
        ADD,
        DEL,
        CLEAR
    }

    private final ArrayList<DelayedAction<T>> delayedActions = new ArrayList<>();

    public DelayedList<T> delayedAdd(T data) {
        DelayedAction<T> action = new DelayedAction<>(DelayedActionType.ADD, data);
        delayedActions.add(action);
        return this;
    }

    public DelayedList<T> delayedDel(T data) {
        DelayedAction<T> action = new DelayedAction<>(DelayedActionType.DEL, data);
        delayedActions.add(action);
        return this;
    }

    public DelayedList<T> delayedClear() {
        DelayedAction<T> action = new DelayedAction<>(DelayedActionType.CLEAR, null);
        delayedActions.add(action);
        return this;
    }

    public void applyDelayedActions() {
        for (DelayedAction<T> action : delayedActions) {
            switch (action.actionType) {
                case ADD -> add(action.data);
                case DEL -> remove(action.data);
                case CLEAR -> clear();
            }
        }
        delayedActions.clear();
    }
}

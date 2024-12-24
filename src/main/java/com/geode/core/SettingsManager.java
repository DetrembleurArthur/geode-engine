package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;

@Singleton
public class SettingsManager extends ResourceManager<Settings> {
    private static SettingsManager instance;

    SettingsManager() throws GeodeException {
        super("settings", Settings.class, Extensions.JSON);
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("settings manager is a singleton");
    }

    public static SettingsManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
    }
}

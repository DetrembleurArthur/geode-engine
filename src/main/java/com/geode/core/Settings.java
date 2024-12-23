package com.geode.core;

import com.geode.config.JsonLoader;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class Settings extends Properties implements Resource {

    private static final Logger logger = LogManager.getLogger(Settings.class);

    private String data;
    private String filename;

    public Settings(String filename) throws IOException {
        this.filename = filename;
    }

    @Override
    public boolean isLoaded() {
        return data != null;
    }

    @Override
    public void close() throws Exception {
        data = null;
        filename = null;
    }

    @Override
    public void init() throws GeodeException {
        try {
            data = JsonLoader.load(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Serializable> T of(Class<T> clazz) throws GeodeException {
        try {
            return JsonLoader.map(data, clazz);
        } catch (IOException e) {
            throw new GeodeException(e);
        }
    }
}

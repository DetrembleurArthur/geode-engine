package com.geode.core;

import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

public class Settings extends Properties implements Resource {

    private static final Logger logger = LogManager.getLogger(Settings.class);

    private Object data;
    private final String filename;
    private Class<?> clazz;

    public Settings(String filename) {
        this.filename = filename;
    }

    private void fetchClassName() {
        int extIndex = filename.lastIndexOf('.');
        if(extIndex != -1) {
            int sepIndex = filename.lastIndexOf('_');
            if(sepIndex != -1) {
                String pojoClassName = filename.substring(sepIndex + 1, extIndex);
                pojoClassName = pojoClassName.substring(0, 1).toUpperCase() + pojoClassName.substring(1);
                pojoClassName = Application.getInstance().getApplicationPackage() + "." + pojoClassName;
                try {
                    clazz = Class.forName(pojoClassName);
                } catch (ClassNotFoundException e) {
                    logger.info("class {} not found -> settings are properties only", pojoClassName);
                }
            }
        }
    }

    private void buildPojo() throws GeodeException {
        if(clazz != null) {
            try {
                data = clazz.getConstructor().newInstance();
                for(String property : stringPropertyNames()) {
                    String[] tokens = property.split("\\.");
                    Method getter = null;
                    Method setter = null;
                    Object currentInnerPojo = data;
                    int i = 0;
                    for(String token : tokens) {
                        String getterName = "get" + token.substring(0, 1).toUpperCase() + token.substring(1);
                        String setterName = "set" + token.substring(0, 1).toUpperCase() + token.substring(1);
                        getter = currentInnerPojo.getClass().getDeclaredMethod(getterName);
                        setter = currentInnerPojo.getClass().getDeclaredMethod(setterName, getter.getReturnType());
                        Object getterValue = getter.invoke(currentInnerPojo);
                        if(i != tokens.length - 1) {
                            if(getterValue == null) {
                                getterValue = getter.getReturnType().getConstructor().newInstance();
                                setter.invoke(currentInnerPojo, getterValue);
                                setter.invoke(currentInnerPojo, getterValue);
                            }
                            currentInnerPojo = getterValue;
                        }
                        i++;
                    }
                    if(getter != null) {
                        Class<?> type = getter.getReturnType();
                        if(Integer.class.isAssignableFrom(type)) {
                            setter.invoke(currentInnerPojo, Integer.parseInt(getProperty(property)));
                        } else if(Double.class.isAssignableFrom(type)) {
                            setter.invoke(currentInnerPojo, Double.parseDouble(getProperty(property)));
                        }
                        else if(Boolean.class.isAssignableFrom(type)) {
                            setter.invoke(currentInnerPojo, Boolean.parseBoolean(getProperty(property)));
                        }
                        else {
                            setter.invoke(currentInnerPojo, getProperty(property));
                        }
                    }
                }
            } catch (Exception e) {
                throw new GeodeException(e);
            }
        }
    }

    @Override
    public boolean isLoaded() {
        return !isEmpty();
    }

    @Override
    public void close() throws Exception {
        clear();
        data = null;
    }

    @Override
    public void init() throws GeodeException {
        try {
            load(new FileReader(filename));
            fetchClassName();
            buildPojo();
        } catch (IOException e) {
            throw new GeodeException(e);
        }
    }

    public <T> T pojo() {
        return (T) data;
    }
}

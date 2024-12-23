package com.geode.config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {

    public static <T extends Serializable> T load(String filename, Class<T> clazz) throws IOException {
        String content = load(filename);
        return map(content, clazz);
    }

    public static <T extends Serializable> T map(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    public static String load(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}

package com.geode.core.reflections;

public enum Extensions {
        NONE(""),
        SHA_GLSL("glsl"),
        TEX_PNG("png"),
        TEX_JPG("jpg"),
        TEX_JPEG("jpeg"),
        SOU_MP3("mp3"),
        SOU_MP4("mp4"),
        SOU_OGG("ogg"),
        SOU_WAV("wav");

    private final String extension;

    Extensions(String extension) {
        this.extension = extension;
    }


    @Override
    public String toString() {
        return extension;
    }
}

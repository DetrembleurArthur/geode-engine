package com.geode.test;

import com.geode.core.Settings;
import com.geode.core.reflections.Artifact;
import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.ResourceHolder;
import com.geode.graphics.Model;
import com.geode.graphics.Shader;
import com.geode.graphics.Texture;

@ResourceHolder("default")
public class MyResources {

    @Artifact(value = "classic", ext = Extensions.SHA_GLSL)
    public Shader classic;

    @Artifact(value = "tex", ext = Extensions.SHA_GLSL)
    public Shader tex;

    @Artifact(value = "3d", ext = Extensions.SHA_GLSL)
    public Shader shader3d;

    @Artifact(value = "yagami", ext = Extensions.TEX_JPG)
    public Texture texture;

    @Artifact(value = "game", ext = Extensions.SETTINGS)
    public Settings gameSettings;

    @Artifact(value = "gun")
    public Model gun;

    @Artifact(value = "cube")
    public Model cube;
}

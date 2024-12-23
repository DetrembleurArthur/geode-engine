package com.geode.test;

import com.geode.core.Settings;
import com.geode.core.reflections.Artifact;
import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.ResourceHolder;
import com.geode.graphics.Model;
import com.geode.graphics.Shader;
import com.geode.graphics.Texture;
import com.geode.graphics.sprite.SpriteSheet;

@ResourceHolder("default")
public class MyResources {

    @Artifact(value = "classic", ext = Extensions.SHA_GLSL)
    public Shader classic;

    @Artifact(value = "tex", ext = Extensions.SHA_GLSL)
    public Shader tex;

    @Artifact(value = "3d", ext = Extensions.SHA_GLSL)
    public Shader shader3d;

    @Artifact(value = "blob", ext = Extensions.TEX_PNG)
    public Texture texture;

    @Artifact(value = "gun")
    public Model gun;

    @Artifact(value = "cube")
    public Model cube;

    @Artifact(value = "blob")
    public SpriteSheet blob_sheet;

    @Artifact(value = "game", ext = Extensions.JSON)
    public Settings gameSettings;
}

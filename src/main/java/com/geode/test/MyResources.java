package com.geode.test;

import com.geode.core.reflections.Artifact;
import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.ResourceHolder;
import com.geode.graphics.Shader;

@ResourceHolder("default")
public class MyResources {

    @Artifact(value = "classic", ext = Extensions.SHA_GLSL)
    public Shader classic;
}

package com.geode.core.registry;

import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera;
import com.geode.graphics.camera.Camera2D;
import com.geode.graphics.camera.Camera3D;
import com.geode.graphics.renderer.FontRenderer;
import com.geode.graphics.renderer.Renderer;
import com.geode.graphics.renderer.ShapeRenderer;
import com.geode.graphics.renderer.TextureRenderer;

@Singleton
public class CameraRegistry extends Registry<Camera> {

    private static CameraRegistry instance;

    public static CameraRegistry getInstance() {
        if (instance == null)
            instance = new CameraRegistry();
        return instance;
    }

    @Override
    public void init() throws GeodeException {
        instance.register(new Camera2D())
                .register(new Camera3D());
    }
}

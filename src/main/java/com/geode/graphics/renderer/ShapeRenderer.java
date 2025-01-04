package com.geode.graphics.renderer;

import com.geode.core.ShaderManager;
import com.geode.core.registry.CameraRegistry;
import com.geode.graphics.camera.Camera2D;

public class ShapeRenderer extends Renderer<Camera2D> {

    public ShapeRenderer() {
        this(CameraRegistry.getInstance().get(Camera2D.class));
    }
    public ShapeRenderer(Camera2D camera2D) {
        super(camera2D, ShaderManager.getInstance().getResource("default.classic"));
    }
}

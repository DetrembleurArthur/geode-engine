package com.geode.graphics.renderer;

import com.geode.core.ShaderManager;
import com.geode.graphics.camera.Camera2D;

public class ShapeRenderer extends Renderer<Camera2D> {

    public ShapeRenderer() {
        this(new Camera2D());
    }
    public ShapeRenderer(Camera2D camera2D) {
        super(camera2D, ShaderManager.getInstance().getResource("default.classic"));
    }
}

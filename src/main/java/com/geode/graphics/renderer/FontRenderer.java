package com.geode.graphics.renderer;

import com.geode.core.ShaderManager;
import com.geode.graphics.camera.Camera2D;

public class FontRenderer extends Renderer<Camera2D> {

    public FontRenderer() {
        this(new Camera2D());
    }
    public FontRenderer(Camera2D camera2D) {
        super(camera2D, ShaderManager.getInstance().getResource("default.font"));
    }
}

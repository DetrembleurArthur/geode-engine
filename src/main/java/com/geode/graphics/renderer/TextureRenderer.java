package com.geode.graphics.renderer;

import com.geode.core.ShaderManager;
import com.geode.graphics.camera.Camera2D;

public class TextureRenderer extends Renderer<Camera2D> {

    public TextureRenderer() {
        this(new Camera2D());
    }
    public TextureRenderer(Camera2D camera2D) {
        super(camera2D, ShaderManager.getInstance().getResource("default.tex"));
    }
}

package com.geode.graphics.renderer;

import com.geode.core.ShaderManager;
import com.geode.core.registry.CameraRegistry;
import com.geode.graphics.camera.Camera2D;

public class TextureRenderer extends Renderer<Camera2D> {

    private boolean colorized = false;

    public TextureRenderer() {
        this(CameraRegistry.getInstance().get(Camera2D.class));
    }

    public TextureRenderer(Camera2D camera2D) {
        super(camera2D, ShaderManager.getInstance().getResource("default.tex"));
    }

    public void setAsColorized() {
        if (!colorized) {
            setShader(ShaderManager.getInstance().getResource("default.tex-colored"));
            colorized = true;
        }
    }

    public boolean isColorized() {
        return colorized;
    }
}

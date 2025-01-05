package com.geode.graphics.renderer;

import com.geode.core.ShaderManager;
import com.geode.core.registry.CameraRegistry;
import com.geode.graphics.camera.Camera2D;

public class FontRenderer extends Renderer<Camera2D> {

    private boolean colorized;

    public FontRenderer() {
        this(CameraRegistry.getInstance().get(Camera2D.class));
    }

    public FontRenderer(Camera2D camera2D) {
        super(camera2D, ShaderManager.getInstance().getResource("default.font"));
        colorized = false;
    }

    public void setAsColorized() {
        if (!colorized) {
            setShader(ShaderManager.getInstance().getResource("default.font-colored"));
            colorized = true;
        }
    }

    public boolean isColorized() {
        return colorized;
    }
}

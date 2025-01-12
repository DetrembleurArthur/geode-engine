package com.geode.entity;

import com.geode.core.components.RendererComponent;
import com.geode.core.registry.RendererRegistry;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import com.geode.graphics.meshing.QuadMeshBuilder;
import com.geode.graphics.renderer.TextureRenderer;
import org.joml.Vector4f;

public class TexturedShape extends Shape {

    public TexturedShape() {
        this(null);
    }

    public TexturedShape(Texture texture) throws GeodeException {
        super();
        RendererComponent component = getComponent(RendererComponent.class);
        component.setRenderer(RendererRegistry.getInstance().get(TextureRenderer.class));
        component.setMesh(new QuadMeshBuilder().build(component.getRenderer().getShader().getMeshAttributes()));
        if (texture != null)
            setTexture(texture);
    }

    public void setTexture(Texture texture) {
        try {
            getComponent(RendererComponent.class).setTexture(texture);
        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setCornerColor(int cornerId, Vector4f color) {
        RendererComponent rendererComponent = getComponent(RendererComponent.class);
        if (!((TextureRenderer) rendererComponent.getRenderer()).isColorized()) {
            ((TextureRenderer) rendererComponent.getRenderer()).setAsColorized();
            rendererComponent.setMesh(new QuadMeshBuilder().build(rendererComponent.getRenderer().getShader().getMeshAttributes()));
        }
        super.setCornerColor(cornerId, color);
    }
}

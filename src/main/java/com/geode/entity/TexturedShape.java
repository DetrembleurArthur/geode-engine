package com.geode.entity;

import com.geode.core.components.RendererComponent;
import com.geode.core.registry.RendererRegistry;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import com.geode.graphics.meshing.QuadMeshBuilder;
import com.geode.graphics.renderer.ShapeRenderer;
import com.geode.graphics.renderer.TextureRenderer;

public class TexturedShape extends Shape {

    public TexturedShape() {
        throw new RuntimeException("missing texture");
    }

    public TexturedShape(Texture texture) throws GeodeException {
        super();
        setTexture(texture);
    }

    @Override
    public void init() throws GeodeException {
        RendererComponent component = getComponent(RendererComponent.class);
        component.setRenderer(RendererRegistry.getInstance().get(TextureRenderer.class));
        component.setMesh(new QuadMeshBuilder().build(component.getRenderer().getShader().getMeshAttributes()));
    }

    public void setTexture(Texture texture) {
        try {
            getComponent(RendererComponent.class).setTexture(texture);
        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }
}

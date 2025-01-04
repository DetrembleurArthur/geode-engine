package com.geode.entity;

import com.geode.core.components.RendererComponent;
import com.geode.core.registry.RendererRegistry;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.meshing.QuadMeshBuilder;
import com.geode.graphics.renderer.ShapeRenderer;

public class Shape extends SpacialGameObject {

    @Override
    public void init() throws GeodeException {
        super.init();
        RendererComponent component = getComponent(RendererComponent.class);
        component.setRenderer(RendererRegistry.getInstance().get(ShapeRenderer.class));
        component.setMesh(new QuadMeshBuilder().build(component.getRenderer().getShader().getMeshAttributes()));
    }
}

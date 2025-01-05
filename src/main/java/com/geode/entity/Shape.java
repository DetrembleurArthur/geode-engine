package com.geode.entity;

import com.geode.core.components.RendererComponent;
import com.geode.core.registry.RendererRegistry;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.meshing.MeshAttributeType;
import com.geode.graphics.meshing.QuadMeshBuilder;
import com.geode.graphics.renderer.ShapeRenderer;
import org.joml.Vector4f;

public class Shape extends SpacialGameObject {

    public Shape() {
        RendererComponent component = getComponent(RendererComponent.class);
        component.setRenderer(RendererRegistry.getInstance().get(ShapeRenderer.class));
        component.setMesh(new QuadMeshBuilder().build(component.getRenderer().getShader().getMeshAttributes()));
    }

    public void setCornerColor(int cornerId, Vector4f color) {
        RendererComponent component = getComponent(RendererComponent.class);
        Mesh mesh = component.getMesh();
        mesh.updateVertex(component.getRenderer().getShader().getMeshAttributes(), MeshAttributeType.COLOR, cornerId, new float[]{color.x, color.y, color.z, color.w});
    }
}

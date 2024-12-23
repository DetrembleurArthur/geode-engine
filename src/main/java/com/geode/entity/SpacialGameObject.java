package com.geode.entity;

import com.geode.core.components.render.RendererComponent;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import com.geode.graphics.renderer.Renderer;
import org.joml.Vector4f;

public class SpacialGameObject extends GameObject {
    private Transform transform = new Transform();

    public Transform getTransform() {
        return transform;
    }

    public Transform tr() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void assignDefaultRenderer(Renderer<?> renderer) throws GeodeException {
        assignDefaultRenderer(renderer, null);
    }

    public void assignDefaultRenderer(Renderer<?> renderer, Texture texture) throws GeodeException {
        assignDefaultRenderer(renderer, texture, null);
    }

    public void assignDefaultRenderer(Renderer<?> renderer, Texture texture, Vector4f[] colors) throws GeodeException {
        RendererComponent rendererComponent = getComponent(RendererComponent.class);
        int[] indexes = new int[]{
                0, 1, 2,
                0, 2, 3,
        };
        if (texture != null) {
            rendererComponent.setTexture(texture);
            if (renderer.getShader().getMeshAttributes().size() == 3) {
                rendererComponent.getMesh().fill(renderer.getShader().getMeshAttributes(), new float[]{
                        // Face arrière (Rouge)
                        0, 0, 0, colors[0].x, colors[0].y, colors[0].z, colors[0].w, 0, 0,  // Sommet 0 : arrière-bas-gauche, rouge
                        0, 1, 0, colors[1].x, colors[1].y, colors[1].z, colors[1].w, 0, 1,// Sommet 1 : arrière-haut-gauche, rouge
                        1, 1, 0, colors[2].x, colors[2].y, colors[2].z, colors[2].w, 1, 1,  // Sommet 2 : arrière-haut-droit, rouge
                        1, 0, 0, colors[3].x, colors[3].y, colors[3].z, colors[3].w, 1, 0// Sommet 3 : arrière-bas-droit, rouge
                }, indexes);
            } else if (renderer.getShader().getMeshAttributes().size() == 2) {
                rendererComponent.getMesh().fill(renderer.getShader().getMeshAttributes(), new float[]{
                        // Face arrière (Rouge)
                        0, 0, 0, 0, 0,  // Sommet 0 : arrière-bas-gauche, rouge
                        0, 1, 0, 0, 1,// Sommet 1 : arrière-haut-gauche, rouge
                        1, 1, 0, 1, 1,  // Sommet 2 : arrière-haut-droit, rouge
                        1, 0, 0, 1, 0// Sommet 3 : arrière-bas-droit, rouge
                }, indexes);
            }

        } else {
            if (renderer.getShader().getMeshAttributes().size() == 2) {
                rendererComponent.getMesh().fill(renderer.getShader().getMeshAttributes(), new float[]{
                        // Face arrière (Rouge)
                        0, 0, 0, colors[0].x, colors[0].y, colors[0].z, colors[0].w,  // Sommet 0 : arrière-bas-gauche, rouge
                        0, 1, 0, colors[1].x, colors[1].y, colors[1].z, colors[1].w,// Sommet 1 : arrière-haut-gauche, rouge
                        1, 1, 0, colors[2].x, colors[2].y, colors[2].z, colors[2].w,  // Sommet 2 : arrière-haut-droit, rouge
                        1, 0, 0, colors[3].x, colors[3].y, colors[3].z, colors[3].w// Sommet 3 : arrière-bas-droit, rouge
                }, indexes);
            } else if (renderer.getShader().getMeshAttributes().size() == 1) {
                rendererComponent.getMesh().fill(renderer.getShader().getMeshAttributes(), new float[]{
                        // Face arrière (Rouge)
                        0, 0, 0,  // Sommet 0 : arrière-bas-gauche, rouge
                        0, 1, 0,// Sommet 1 : arrière-haut-gauche, rouge
                        1, 1, 0,  // Sommet 2 : arrière-haut-droit, rouge
                        1, 0, 0// Sommet 3 : arrière-bas-droit, rouge
                }, indexes);
            }
        }
        rendererComponent.setRenderer(renderer);
    }
}

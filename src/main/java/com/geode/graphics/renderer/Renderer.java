package com.geode.graphics.renderer;

import com.geode.entity.Transform;
import com.geode.graphics.Shader;
import com.geode.graphics.Texture;
import com.geode.graphics.camera.Camera;
import com.geode.graphics.meshing.Mesh;
import org.joml.Vector4f;

public class Renderer<T extends Camera> {
    protected T camera;
    protected Shader shader;
    protected Vector4f color = new Vector4f(1, 1, 1, 1);


    public Renderer() {
    }

    public Renderer(T camera, Shader shader) {
        this.camera = camera;
        this.shader = shader;
    }

    public void render(Transform transform, Mesh mesh) {
        shader.setUniformMatrix4fv(transform.getModel(), "uModel");
        shader.setUniformMatrix4fv(camera.updateProjection(), "uProjection");
        shader.setUniformMatrix4fv(camera.updateView(), "uView");
        shader.setUniformVector4fv(color, "uColor");
        mesh.uploadToGPU();
    }

    public void render(Transform transform, Mesh mesh, Texture texture) {
        shader.setUniformMatrix4fv(transform.getModel(), "uModel");
        shader.setUniformMatrix4fv(camera.updateProjection(), "uProjection");
        shader.setUniformMatrix4fv(camera.updateView(), "uView");
        shader.setUniformVector4fv(color, "uColor");
        texture.active();
        texture.bind();
        shader.setUniformTexture(0, "texture1");
        mesh.uploadToGPU();
        texture.unbind();
    }

    public void start() {
        shader.use();
    }

    public void stop() {
        shader.unuse();
    }

    public T getCamera() {
        return camera;
    }

    public void setCamera(T camera) {
        this.camera = camera;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public boolean isReady() {
        return shader != null && shader.isLoaded() && camera != null;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }
}

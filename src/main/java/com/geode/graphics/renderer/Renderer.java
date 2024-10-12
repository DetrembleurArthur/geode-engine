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
        shader.setUniformVector4fv(new Vector4f(1, 1, 0, 1), "uColor");
        mesh.uploadToGPU();
    }

    public void render(Transform transform, Mesh mesh, Texture texture) {
        shader.setUniformMatrix4fv(transform.getModel(), "uModel");
        shader.setUniformMatrix4fv(camera.updateProjection(), "uProjection");
        shader.setUniformMatrix4fv(camera.updateView(), "uView");
        shader.setUniformVector4fv(new Vector4f(1, 1, 0, 1), "uColor");
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
}

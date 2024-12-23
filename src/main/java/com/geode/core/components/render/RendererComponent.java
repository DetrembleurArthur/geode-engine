package com.geode.core.components.render;

import com.geode.core.components.base.StaticComponent;
import com.geode.entity.GameObject;
import com.geode.entity.SpacialGameObject;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.renderer.Renderer;

public class RendererComponent extends StaticComponent {

    private Renderer<?> renderer;
    private Mesh mesh;
    private Texture texture;

    public RendererComponent(GameObject child) {
        super(child);
    }

    @Override
    public void init() throws GeodeException {
        mesh = new Mesh();
        mesh.init();
    }

    @Override
    public void close() throws Exception {
        if (mesh != null && this.mesh.isReady()) {
            mesh.close();
        }
    }

    public Renderer<?> getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer<?> renderer) {
        this.renderer = renderer;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        if (this.mesh != null && this.mesh.isReady()) {
            try {
                this.mesh.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mesh = mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        mesh.setTextured(this.texture != null);
    }

    public void render() {
        if (renderer != null && mesh != null && mesh.isReady()) {
            renderer.start();
            SpacialGameObject spacial = (SpacialGameObject) child;
            if (texture != null) {
                renderer.render(spacial.tr(), mesh, texture, spacial.getColor());
            } else {
                renderer.render(spacial.tr(), mesh, spacial.getColor());
            }
            renderer.stop();
        }
    }
}

package com.geode.test;

import com.geode.core.*;
import com.geode.core.components.render.RendererComponent;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.entity.SpacialGameObject;
import com.geode.entity.Transform;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera2D;
import com.geode.graphics.meshing.MeshAttribute;
import com.geode.graphics.renderer.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@SceneEntry(value = "my_2d_scene", first = true)
public class My2DScene extends Scene {

    public @Inject Application app;
    public @Inject WindowManager windowManager;
    public @Inject SceneManager sceneManager;
    public @Inject MySingleton singleton;
    public @Inject WindowEventsManager eventsManager;
    public @Inject MouseManager mouseManager;
    public @Inject KeyManager keyManager;

    public @Inject MyResources resources;

    public @Inject ControllerManager controllerManager;

    private SpacialGameObject gameObject;

    @Override
    public void init() {
        try {
            resources.classic.load();
            resources.tex.load();
            resources.texture.load();
            resources.gameSettings.load();
            System.err.println(resources.gameSettings.<Game>pojo().toString());

        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void select() throws GeodeException {

        windowManager.getWindow().setClearColor(new Vector4f(0f, 0, 0, 1));
        singleton.sayHello();




        gameObject = new SpacialGameObject();
        gameObject.init();
        RendererComponent rendererComponent = gameObject.getComponent(RendererComponent.class);
        rendererComponent.setTexture(resources.texture);
        rendererComponent.setRenderer(new Renderer<>(new Camera2D(windowManager), resources.tex));
        rendererComponent.getMesh().fill(resources.tex.getMeshAttributes(), new float[]{
                // Face arrière (Rouge)
                0, 0, 0,   1, 1, 1, 1,     0, 0,  // Sommet 0 : arrière-bas-gauche, rouge
                0, 1, 0,   1, 1, 1, 1,     0, 1,// Sommet 1 : arrière-haut-gauche, rouge
                1, 1, 0,   1, 1, 1, 1,     1, 1,  // Sommet 2 : arrière-haut-droit, rouge
                1, 0, 0,   1, 1, 1, 1,     1, 0// Sommet 3 : arrière-bas-droit, rouge
        }, new int[]{
                0, 1, 2,
                0, 2, 3,
        });

        Transform transform = gameObject.getTransform();




        transform.setPosition(new Vector3f(100, 100, 0));
        transform.setSize(new Vector3f(300, 300, 0));
        transform.setCenterOrigin();

        ((Camera2D)rendererComponent.getRenderer().getCamera()).focus(new Vector2f(transform.getPosition().x, transform.getPosition().y));


    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(double dt) throws GeodeException {
        RendererComponent rendererComponent = gameObject.getComponent(RendererComponent.class);
        if(rendererComponent != null) {
            rendererComponent.render();
        }

    }

    @Override
    public void close() throws Exception {
        gameObject.close();
    }
}

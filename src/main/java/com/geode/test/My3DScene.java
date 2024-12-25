package com.geode.test;

import com.geode.core.*;
import com.geode.core.components.RendererComponent;
import com.geode.core.mouse.ButtonState;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.entity.SpacialGameObject;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera3D;
import com.geode.graphics.renderer.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

@SceneEntry(value = "my_3d_scene", first = false)
public class My3DScene extends Scene {

    public @Inject Application app;
    public @Inject WindowManager windowManager;
    public @Inject SceneManager sceneManager;
    public @Inject MySingleton singleton;
    public @Inject WindowEventsManager eventsManager;
    public @Inject MouseManager mouseManager;
    public @Inject KeyManager keyManager;

    public @Inject MyResources resources;


    private SpacialGameObject gameObject;
    private SpacialGameObject cube;
    private Camera3D camera3D;
    private Camera3D localCamera;


    @Override
    public void init() {


    }

    @Override
    public void select() throws GeodeException {
        windowManager.getWindow().setClearColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
        singleton.sayHello();

        gameObject = new SpacialGameObject();
        gameObject.init();

        try {
            resources.shader3d.init();
            resources.gun.init();
            resources.cube.init();
            resources.texture.init();

            camera3D = new Camera3D(windowManager);
            localCamera = new Camera3D(windowManager);
            RendererComponent rendererComponent = gameObject.getComponent(RendererComponent.class);
            rendererComponent.setTexture(resources.gun.getTextures().get(0));
            rendererComponent.setRenderer(new Renderer<>(localCamera, resources.shader3d));
            rendererComponent.setMesh(resources.gun.getMesh());
            camera3D.getProjectionSettings().setZfar(1000000);
            camera3D.enableDepthTest();

            gameObject.getTransform().setSize(new Vector3f(0.001f, 0.001f, 0.001f));
            gameObject.getTransform().setPosition(localCamera.getPosition());
            gameObject.getTransform().getRotation().x += 90;
            gameObject.getTransform().getRotation().y += 180;
            gameObject.getTransform().getPosition().add(0.01f, -0.015f, -0.04f);
            //gameObject.getTransform().setOrigin(camera3D.getPosition());

            cube = new SpacialGameObject();
            cube.init();
            rendererComponent = cube.getComponent(RendererComponent.class);
            rendererComponent.setTexture(resources.cube.getTextures().get(0));
            rendererComponent.setRenderer(new Renderer<>(camera3D, resources.shader3d));
            rendererComponent.setMesh(resources.cube.getMesh());
            cube.getTransform().setSize(new Vector3f(1000, 1000, 1000));



        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }

        mouseManager.lock();





        //renderer.getCamera().focus(new Vector2f(transform.getPosition().x, transform.getPosition().y));


    }

    float v = 0;
    @Override
    public void update(double dt) {

        //cube.getTransform().setPosition(camera3D.getPosition());

       // gameObject.getTransform().setOrigin(camera3D.getPosition());
        //cube.getTransform().setRotation(cameraRotation);

        /*cube.getTransform().getRotation().x = camera3D.getHorizontalAngle();
        cube.getTransform().getRotation().y = camera3D.getVerticalAngle();*/
        //camera3D.updateFps(new Vector2f(mouseManager.getPosition()), cube.getTransform().getPosition(), cube.getTransform().getRotation());
        camera3D.update(new Vector2f(mouseManager.getPosition()));

        camera3D.activateKeys();
        cube.getTransform().setDirty(true);

        if(MouseManager.getInstance().isRightButton(ButtonState.PRESSED)) {
            gameObject.getTransform().setPosition(new Vector3f(0f, -0.011f, -0.020f).add(localCamera.getPosition()));
            app.getWindowManager().adaptViewport(localCamera);
            app.getWindowManager().adaptViewport(camera3D);
        } else {
            gameObject.getTransform().setPosition(new Vector3f(0.01f, -0.015f, -0.04f).add(localCamera.getPosition()));
        }
    }

    @Override
    public void draw(double dt) throws GeodeException {
        RendererComponent rendererComponent;

        rendererComponent = cube.getComponent(RendererComponent.class);
        if(rendererComponent != null) {
            rendererComponent.draw();
        }
        rendererComponent = gameObject.getComponent(RendererComponent.class);
        if(rendererComponent != null) {
            rendererComponent.draw();
        }
    }

    @Override
    public void close() throws Exception {
        if(gameObject != null)
            gameObject.close();
        if(cube != null)
            cube.close();
    }
}

package com.geode.test;

import com.geode.core.*;
import com.geode.core.components.render.RendererComponent;
import com.geode.core.mouse.ButtonState;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.time.Timer;
import com.geode.entity.SpacialGameObject;
import com.geode.entity.Transform;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera2D;
import com.geode.graphics.renderer.Renderer;
import com.geode.graphics.sprite.Sprite;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

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


    private boolean pressed = false;

    private Timer timer;

    @Override
    public void init() {
        try {
            resources.classic.load();
            resources.texture.load();
            resources.blob_sheet.init();
            resources.gameSettings.init();

        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void select() throws GeodeException {
        System.err.println(resources.gameSettings.of(MyGameConfig.class).getTitle());
        windowManager.getWindow().setClearColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
        singleton.sayHello();

        gameObject = new SpacialGameObject();
        gameObject.getColor().x = 0;
        gameObject.getColor().z = 0;
        gameObject.assignDefaultRenderer(getDefault2DRenderer(), resources.texture);

        Transform transform = gameObject.getTransform();


        transform.setPosition(new Vector3f(100, 100, 0));
        transform.setSize(new Vector3f(300, 300, 0));
        transform.setCenterOrigin();

        //gameObject.getComponent(LambdaComponent.class).set(() -> gameObject.tr().rotate((float) (90 * Time.getDelta())));

        goManager.layer().add(gameObject);

        default2DCamera.focus(new Vector2f(transform.getPosition().x, transform.getPosition().y));

        timer = new Timer(3);

    }

    @Override
    public void update(double dt) {
        if (!pressed && mouseManager.isLeftButton(ButtonState.PRESSED)) {
            pressed = true;
            try {
                Sprite sprite = resources.blob_sheet.getAnimation("down").get();
                resources.blob_sheet.getAnimation("down").next();
                gameObject.getComponent(RendererComponent.class).getMesh().setRectUVs(sprite.getPosition(), sprite.getSize());
            } catch (GeodeException e) {
                throw new RuntimeException(e);
            }
        } else if (mouseManager.isLeftButton(ButtonState.RELEASED)) {
            pressed = false;
        }
    }

    @Override
    public void close() throws Exception {

    }
}

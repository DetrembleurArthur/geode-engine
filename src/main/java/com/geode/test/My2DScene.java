package com.geode.test;

import com.geode.core.*;
import com.geode.core.components.LambdaComponent;
import com.geode.core.components.RendererComponent;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.registry.RendererRegistry;
import com.geode.core.time.ActionTimer;
import com.geode.core.time.Time;
import com.geode.core.time.Timer;
import com.geode.entity.SpacialGameObject;
import com.geode.entity.TexturedShape;
import com.geode.entity.ui.Text;
import com.geode.entity.ui.TextAlignment;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Colors;
import com.geode.graphics.camera.Camera2D;
import com.geode.graphics.meshing.MeshAttributeType;
import com.geode.graphics.renderer.FontRenderer;
import com.geode.graphics.renderer.ShapeRenderer;
import com.geode.graphics.ui.text.FontCharsets;
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

    private Text text;

    @Override
    public void init() {
        try {

            resources.geode.load();

            resources.terraria_font.configure(120, FontCharsets.ascii());
            resources.terraria_font.load();

        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void select() throws GeodeException {
        //System.err.println(resources.gameSettings.of(MyGameConfig.class).getTitle());
        windowManager.getWindow().setClearColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));

        text = new Text("Arthur", resources.terraria_font);
        text.setColor(Colors.white());

        text.setAlignment(TextAlignment.CENTER);
        text.setCharacterColors(3, new Vector4f[]{
                Colors.orange(),
                Colors.red(),
                Colors.blue(),
                Colors.green()
        });



        text.tr().setCenterOrigin();
        text.setTextWidth(300);
        text.tr().setPosition(new Vector3f(0, 0, 0));




        text.getComponent(LambdaComponent.class).set(() -> {
            //text.tr().translateX((float) (50*Time.getDelta()));
        });


        TexturedShape gameObject = new TexturedShape(resources.geode);
        gameObject.tr().setSize(resources.geode.getSize().mul(3));
        gameObject.tr().setCenterOrigin();
        gameObject.tr().setPosition(windowManager.getWindow().getSize().div(2));
        gameObject.getComponent(LambdaComponent.class).set(() -> {
            //gameObject.tr().rotate((float) (45.0f * Time.getDelta()));
            gameObject.tr().setPosition(mouseManager.getPositionFromCamera2D());
        });

        goManager.layer().add(gameObject);
        goManager.layer().add(text);

        text.tr().setParentModel(gameObject.tr().getModel());


        //default2DCamera.focus(new Vector2f(text.tr().getPosition().x, text.tr().getPosition().y));

    }

    @Override
    public void update(double dt) {
        /*
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
        }*/
    }

    @Override
    public void close() throws Exception {

    }
}

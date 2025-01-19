package com.geode.test;

import com.geode.core.*;
import com.geode.core.collider.Collider;
import com.geode.core.components.*;
import com.geode.core.events.EventClick;
import com.geode.core.key.KeyCommand;
import com.geode.core.key.Keys;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.time.ActionTimer;
import com.geode.core.time.Time;
import com.geode.entity.Shape;
import com.geode.entity.TexturedShape;
import com.geode.entity.ui.Text;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Colors;
import com.geode.graphics.ui.text.FontCharsets;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;

@SceneEntry(value = "my_sprite_2d_scene", first = true)
public class MySprite2DScene extends Scene {

    public @Inject Application app;
    public @Inject WindowManager windowManager;
    public @Inject SceneManager sceneManager;
    public @Inject MySingleton singleton;
    public @Inject WindowEventsManager eventsManager;
    public @Inject MouseManager mouseManager;
    public @Inject KeyManager keyManager;

    public @Inject MyResources resources;

    public @Inject ControllerManager controllerManager;


    @Override
    public void init() {
        try {
            resources.terraria_font.configure(30, FontCharsets.ascii());
            resources.terraria_font.load();
            //resources.texture.load();
            resources.blob_sheet.load();

        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }

    private TexturedShape blob, blob2;

    @Override
    public void select() throws GeodeException {
        //System.err.println(resources.gameSettings.of(MyGameConfig.class).getTitle());
        windowManager.getWindow().setClearColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));

        //default2DCamera.focus(new Vector2f(text.tr().getPosition().x, text.tr().getPosition().y));


        blob = new TexturedShape();
        blob.tr().setWidth(100);
        blob.tr().setHeight(100);
        blob.tr().setCenterOrigin();
        blob.c_sprite()
                .setSpriteSheet(resources.blob_sheet)
                .setAnimation("down")
                .setAnimationDelay(0.4);
        blob.c_lambda()
                .set(() -> {
                    blob.tr().rotate(Time.deltify(-45));
                    if (keyManager.isKeyPressed(Keys.UP)) {
                        blob.tr().translateY(Time.deltify(-250f));
                    }
                    if (keyManager.isKeyPressed(Keys.DOWN)) {
                        blob.tr().translateY(Time.deltify(250f));
                    }
                    if (keyManager.isKeyPressed(Keys.RIGHT)) {
                        blob.tr().translateX(Time.deltify(250f));
                    }
                    if (keyManager.isKeyPressed(Keys.LEFT)) {
                        blob.tr().translateX(Time.deltify(-250f));
                    }
                });

        Text text = new Text("Arthur", resources.terraria_font);
        text.setTextHeight(40);
        text.setColor(Colors.black());
        text.tr().setCenterOrigin();
        text.tr().setX(blob.tr().getSize().x / 2);
        text.tr().setY(-5);


        blob.c_hierarchy().addChild(text);

        blob.c_timer().add(new ActionTimer(10)
                .setOnRun((tm) -> text.setValue((int) tm + " seconds"))
                .setOnStop(() -> text.setValue("completed")));

        blob2 = new TexturedShape();
        blob2.tr().setWidth(100);
        blob2.tr().setHeight(100);
        blob2.tr().setRotation2D(45);
        blob2.c_lambda().set(() -> blob2.tr().rotate(Time.deltify(45)));
        blob2.tr().setCenterOrigin();
        blob2.c_sprite()
                .setSpriteSheet(resources.blob_sheet)
                .setAnimation("down")
                .setAnimationDelay(0.4);

        keyManager.setEnableKeyCommands(true);
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("up"), false)
                .key(Keys.UP));
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("left"), false)
                .key(Keys.LEFT));
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("right"), false)
                .key(Keys.RIGHT));
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("down"), false)
                .key(Keys.DOWN));

        blob.c_collider().createCollider(new Vector2f(0.25f, 0.25f), new Vector2f(0.5f, 1.5f));
        blob2.c_collider().createCollider();
        blob2.tr().setBottomRightPosition(new Vector3f(500, 500, 0));

        text.c_collider().createCollider();

        text.c_collider().showColliders();
        blob.c_collider().showColliders();
        blob2.c_collider().showColliders();


        getGoManager().layer().add(blob).add(blob2);

        blob.c_event().registerEvent(EventClick.class)
                .setAction(EventClick.class, sourceEvent -> System.err.println(sourceEvent.<EventClick>as().getMp()));


    }

    @Override
    public void update(double dt) {
        if (blob.c_collider().isCollision(blob2)) {
            blob.setColor(Colors.red());
            blob2.setColor(Colors.red());
            //blob2.tr().setHeight(300);
        } else {
            blob.setColor(Colors.green());
            blob2.setColor(Colors.green());
        }
    }

    @Override
    public void close() throws Exception {

    }
}

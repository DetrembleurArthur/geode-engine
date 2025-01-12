package com.geode.test;

import com.geode.core.*;
import com.geode.core.components.HierarchyComponent;
import com.geode.core.components.LambdaComponent;
import com.geode.core.components.SpriteComponent;
import com.geode.core.key.KeyCommand;
import com.geode.core.key.Keys;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.time.Time;
import com.geode.entity.Shape;
import com.geode.entity.TexturedShape;
import com.geode.entity.ui.Text;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Colors;
import com.geode.graphics.ui.text.FontCharsets;
import org.joml.Vector2i;
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

    @Override
    public void select() throws GeodeException {
        //System.err.println(resources.gameSettings.of(MyGameConfig.class).getTitle());
        windowManager.getWindow().setClearColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));

        //default2DCamera.focus(new Vector2f(text.tr().getPosition().x, text.tr().getPosition().y));


        TexturedShape blob = new TexturedShape();
        blob.tr().setWidth(100);
        blob.tr().setHeight(100);
        blob.tr().setCenterOrigin();
        blob.setColor(Colors.green());
        blob.getComponent(SpriteComponent.class)
                .setSpriteSheet(resources.blob_sheet)
                .setAnimation("down")
                .setAnimationDelay(0.4);
        blob.getComponent(LambdaComponent.class)
                .set(() -> {
                    if (keyManager.isKeyPressed(Keys.UP)) {
                        blob.tr().translateY((float) (-250.f * Time.getDelta()));
                    }
                    if (keyManager.isKeyPressed(Keys.DOWN)) {
                        blob.tr().translateY((float) (250.f * Time.getDelta()));
                    }
                    if (keyManager.isKeyPressed(Keys.RIGHT)) {
                        blob.tr().translateX((float) (250.f * Time.getDelta()));
                    }
                    if (keyManager.isKeyPressed(Keys.LEFT)) {
                        blob.tr().translateX((float) (-250.f * Time.getDelta()));
                    }
                });

        Text text = new Text("Arthur", resources.terraria_font);
        text.setTextHeight(30);
        text.setColor(Colors.black());
        text.tr().setCenterOrigin();
        text.tr().setX(blob.tr().getSize().x / 2);
        text.tr().setY(-5);

        Shape shape = new Shape();
        shape.tr().setWidth(30);
        shape.tr().setHeight(30);
        shape.tr().setPosition(new Vector2i(0, 0));
        shape.setCornerColor(0, Colors.red());
        shape.setCornerColor(1, Colors.blue());
        shape.setCornerColor(2, Colors.green());
        shape.setCornerColor(3, Colors.yellow());

        getGoManager().layer().add(blob);

        blob.getComponent(HierarchyComponent.class).addChild(text);
        text.getComponent(HierarchyComponent.class).addChild(shape);
        text.getComponent(LambdaComponent.class).set(() -> text.tr().rotate((float) (45 * Time.getDelta())));

        keyManager.setEnableKeyCommands(true);
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("up"), false)
                .key(Keys.UP));
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("left"), false)
                .key(Keys.LEFT));
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("right"), false)
                .key(Keys.RIGHT));
        keyManager.addCommand(new KeyCommand(() -> blob.getComponent(SpriteComponent.class).setAnimation("down"), false)
                .key(Keys.DOWN));

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void close() throws Exception {

    }
}

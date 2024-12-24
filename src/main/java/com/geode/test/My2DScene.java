package com.geode.test;

import com.geode.core.*;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.time.Timer;
import com.geode.entity.SpacialGameObject;
import com.geode.entity.Transform;
import com.geode.entity.ui.Text;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.ui.text.Font;
import com.geode.graphics.ui.text.FontCharsets;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;

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


    private boolean pressed = false;

    private Timer timer;

    @Override
    public void init() {
        try {
            resources.classic.load();
            resources.texture.load();
            resources.blob_sheet.init();
            resources.gameSettings.init();

            resources.vintage_font.configure(150, FontCharsets.ALPHA_NUMERIC);
            resources.vintage_font.load();

        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void select() throws GeodeException {
        System.err.println(resources.gameSettings.of(MyGameConfig.class).getTitle());
        windowManager.getWindow().setClearColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));

        text = new Text("ARTHUR", resources.vintage_font, getFontRenderer());


        text.setTextHeight(200);
        text.tr().setCenterOrigin();

        //gameObject.getComponent(LambdaComponent.class).set(() -> gameObject.tr().rotate((float) (90 * Time.getDelta())));

        goManager.layer().add(text);



        default2DCamera.focus(new Vector2f(text.tr().getPosition().x, text.tr().getPosition().y));

        timer = new Timer(3);

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

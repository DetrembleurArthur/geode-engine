package com.geode.test;

import com.geode.core.*;
import com.geode.core.MouseManager;
import com.geode.core.controller.Controller;
import com.geode.core.controller.Gamepad;
import com.geode.core.key.KeyCommand;
import com.geode.core.key.KeyMods;
import com.geode.core.key.KeyState;
import com.geode.core.key.Keys;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.winevents.WinEvents;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Random;

@SceneEntry(value = "my_scene", first = true)
public class MyScene extends Scene {

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
        windowManager.getWindow().setClearColor(new Vector4f(1, 0, 0, 1));
        singleton.sayHello();

    }

    @Override
    public void update(double dt) {
        if(controllerManager.hasControllers()) {
            Controller controller = controllerManager.getAvailableController();

            if(controller.isPressedOnce(Gamepad.A))
                windowManager.getWindow().setPosition(new Vector2f(new Random().nextInt()%1000, new Random().nextInt()%800));
        }
    }

    @Override
    public void draw(double dt) {

    }

    @Override
    public void close() throws Exception {

    }
}

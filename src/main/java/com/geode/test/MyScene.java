package com.geode.test;

import com.geode.core.*;
import com.geode.core.MouseManager;
import com.geode.core.key.KeyCommand;
import com.geode.core.key.KeyMods;
import com.geode.core.key.KeyState;
import com.geode.core.key.Keys;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.core.winevents.WinEvents;
import org.joml.Vector4f;

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

    @Override
    public void init() {
        windowManager.getWindow().setClearColor(new Vector4f(1, 0, 0, 1));
        singleton.sayHello();
        eventsManager.getWindowKeyEventHandler().setConcurrentProof(true);
        eventsManager.onClosed(WinEvents.CloseCallback.tagged(tag(), () -> System.err.println("it works !")));
        eventsManager.onUnfocused(WinEvents.UnfocusedCallback.tagged(tag(), () -> eventsManager.removeByTag(this)));
        keyManager.addCommand(new KeyCommand(() -> System.err.println("COMMAND SETUP"), true)
                .key(Keys.SPACE).mod(KeyMods.CONTROL).mod(KeyMods.ALT).setState(KeyState.REPEATED));

        System.err.println(resources.classic);
    }

    @Override
    public void update(double dt) {
        if(keyManager.isKeyPressed(Keys.SPACE)) {
            System.err.println("hello");
        }
    }

    @Override
    public void draw(double dt) {

    }

    @Override
    public void close() throws Exception {

    }
}

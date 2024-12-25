package com.geode.test;

import com.geode.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {


        try(Application application = Application.create(Main.class.getPackage().getName())) {
            application.getWindowManager().setHintCallback(() -> {
                //application.getWindowManager().hintMaximized(true);
            });
            application.setFps(600);
            application.init();
            application.run();
        }
    }
}
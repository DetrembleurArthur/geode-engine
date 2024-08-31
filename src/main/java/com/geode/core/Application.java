package com.geode.core;

import com.geode.core.reflections.PackageClassLoaderManager;
import com.geode.core.reflections.SceneEntry;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class Application implements Initializable, Runnable, AutoCloseable {

    private static final Logger logger = LogManager.getLogger(Application.class);
    private static Application instance = null;

    private final WindowManager windowManager;
    private final String applicationPackage;
    private final PackageClassLoaderManager packageClassLoaderManager;
    private final SceneManager sceneManager;

    private Application(String applicationPackage) throws GeodeException {
        this.applicationPackage = applicationPackage;
        windowManager = new WindowManager();
        packageClassLoaderManager = new PackageClassLoaderManager();
        packageClassLoaderManager.register(PackageClassLoaderManager.Defaults.SCENES, applicationPackage,
                Scene.class, SceneEntry.class);
        sceneManager = new SceneManager(packageClassLoaderManager.get(PackageClassLoaderManager.Defaults.SCENES));
    }

    @Override
    public void init() throws GeodeException {
        logger.info("initializing...");
        windowManager.init();
        sceneManager.init();
        logger.info("initialized !");
    }

    @Override
    public void run() {
        logger.info("running...");
        Window window = windowManager.getWindow();
        while(!window.mustBeClosed()) {
            loop(window);
        }
    }

    @Override
    public void close() throws Exception {
        logger.info("closing...");
        sceneManager.close();
        windowManager.close();
        GLFW.glfwSetErrorCallback(null).free();
        logger.info("closed !");
    }

    private void loop(Window window) {
        Scene currentScene = sceneManager.getCurrent();
        double delta = Time.auto_update_delta();
        window.clear();
        currentScene.update(delta);
        currentScene.draw(delta);
        window.swap();
        windowManager.manageEvents();
    }

    public static Application create(String applicationPackage) throws GeodeException {
        if(Application.instance == null) {
            Application.instance = new Application(applicationPackage);
            return Application.instance;
        }
        throw new GeodeException("Can not create more than one Application");
    }

    public static Application getInstance() {
        return Application.instance;
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }
}

package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.PackageClassLoaderManager;
import com.geode.core.reflections.SceneEntry;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Shader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class Application implements Initializable, Runnable, AutoCloseable {

    private static final Logger logger = LogManager.getLogger(Application.class);
    private static Application instance = null;

    private final WindowManager windowManager;
    private final ResourceManagers resourceManagers;
    private final ResourceLocator resourceLocator;
    private final String applicationPackage;
    private final PackageClassLoaderManager packageClassLoaderManager;
    private final SceneManager sceneManager;

    private Application(String applicationPackage) throws GeodeException {
        this.applicationPackage = applicationPackage;
        resourceLocator = new ResourceLocator();
        resourceManagers = new ResourceManagers();
        windowManager = new WindowManager();
        packageClassLoaderManager = new PackageClassLoaderManager();
        packageClassLoaderManager.register(PackageClassLoaderManager.Defaults.SCENES, applicationPackage,
                Scene.class, SceneEntry.class);
        sceneManager = new SceneManager(packageClassLoaderManager.get(PackageClassLoaderManager.Defaults.SCENES));
    }

    @Override
    public void init() throws GeodeException {
        logger.info("initializing...");
        resourceLocator.setResourceFolder("./src/main/resources/");
        resourceLocator.setLocation(Shader.class, "shaders");
        windowManager.init();
        resourceManagers.init();
        resourceManagers.register(Shader.class, new ShaderManager());
        resourceManagers.get(Shader.class).addResource("classic", "default", Extensions.SHA_GLSL, resourceLocator);
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
        resourceManagers.close();
        windowManager.close();
        Objects.requireNonNull(GLFW.glfwSetErrorCallback(null)).free();
        logger.info("closed !");
    }

    public void loop(Window window) {
        windowManager.updateControllers();
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

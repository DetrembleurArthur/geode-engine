package com.geode.core;

import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

@Singleton
public class WindowManager implements Initializable, Closeable {

    private static final Logger logger = LogManager.getLogger(WindowManager.class);
    private static WindowManager instance;
    private Window window;
    private WindowEventsManager windowEventsManager;
    private MouseManager mouseManager;
    private KeyManager keyManager;
    private ControllerManager controllerManager;
    private boolean glfwInitialized = false;
    private Vector4i viewport = new Vector4i();
    private Runnable eventPolicyRunner;
    private Runnable hintCallback;

    public static WindowManager getInstance() {
        return instance;
    }

    WindowManager() throws GeodeException {
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("WindowManager is a singleton");
    }

    @Override
    public void init() throws GeodeException {
        if(!glfwInitialized) {
            logger.info("initializing...");
            glfwInitialized = GLFW.glfwInit();
            if(glfwInitialized) {
                GLFWErrorCallback.createPrint(System.err).set();
                logger.info("glfw version: {}", GLFW.glfwGetVersionString());
                glfwWindowHint(GLFW_SAMPLES, 8);
                glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
                glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
                glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
                glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
                glfwWindowHint(GLFW_ALPHA_BITS, 8);
                if(hintCallback != null)
                    hintCallback.run();
                logger.info("creating Window...");
                window = new Window("", new Vector2i(1400, 900));
                pollEventPolicy();
                logger.info("Window created !");
                window.makeCurrent();
                windowEventsManager = new WindowEventsManager(window);
                mouseManager = new MouseManager(window);
                keyManager = new KeyManager(window);
                controllerManager = new ControllerManager();
                windowEventsManager.init();
                mouseManager.init();
                keyManager.init();
                controllerManager.init();
                viewport = new Vector4i(0, 0, window.getSize().x, window.getSize().y);
                GL.createCapabilities();
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_MULTISAMPLE);
                glEnable(GL_LINE_SMOOTH);
                GL11.glDisable(GL11.GL_CULL_FACE); // Active le culling des faces
            } else {
                logger.error("glfw can not be initialized");
                throw new GeodeException("glfw initialization failed");
            }
            logger.info("initialized !");
        }
        else {
            logger.warn("ignore init because glfw is already initialized");
        }
    }

    @Override
    public void close() throws Exception {
        logger.info("closing...");
        controllerManager.close();
        mouseManager.close();
        keyManager.close();
        windowEventsManager.close();
        window.close();
        glfwTerminate();
        logger.info("closed !");
    }

    public void setHintCallback(Runnable hintCallback) {
        this.hintCallback = hintCallback;
    }

    public boolean isGlfwInitialized() {
        return glfwInitialized;
    }

    public Vector4i getViewport() {
        return viewport;
    }

    public Window getWindow() {
        return window;
    }

    public void pollEventPolicy() {
        eventPolicyRunner = org.lwjgl.glfw.GLFW::glfwPollEvents;
    }

    public void waitEventPolicy() {
        eventPolicyRunner = org.lwjgl.glfw.GLFW::glfwWaitEvents;
    }

    public void updateControllers() {

        if(controllerManager.hasControllers()) {
            controllerManager.update();
        }
    }

    public void manageEvents() {
        eventPolicyRunner.run();
    }

    public void hintResizable(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintVisible(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintDecorated(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintFocused(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUSED, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintAutoIconify(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintFloating(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintMaximized(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintCenterCursor(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_CENTER_CURSOR, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintTransparentFramebuffer(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintFocusOnShow(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void hintXPosition(int x) {
        GLFW.glfwWindowHint(GLFW.GLFW_POSITION_X, x);
    }

    public void hintYPosition(int y) {
        GLFW.glfwWindowHint(GLFW.GLFW_POSITION_Y, y);
    }

    public WindowEventsManager getWindowEventsManager() {
        return windowEventsManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public Runnable getEventPolicyRunner() {
        return eventPolicyRunner;
    }

    public Runnable getHintCallback() {
        return hintCallback;
    }

    public void adaptViewport(Camera camera) {
        Vector2i size = getWindow().getSize();
        setViewport(new Vector4i(0, 0, size.x, size.y));
        window.resetVpAspectRatio();
        if(camera != null) {
            camera.adaptOnResize(this);
        }
    }

    void resize(Vector2i size, Camera camera) {
        Vector2i wsize = window.getSize();
        size.x = size.x > 0 ? size.x : wsize.x;
        size.y = size.y > 0 ? size.y : wsize.y;
        window.setSize(size);
        adaptViewport(camera);
    }

    public void setViewport(Vector4i viewport) {
        this.viewport = viewport;
        glViewport(viewport.x, viewport.y, viewport.z, viewport.w);
    }
}

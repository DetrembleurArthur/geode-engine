package com.geode.core;

import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class WindowManager implements Initializable, AutoCloseable {

    private static final Logger logger = LogManager.getLogger(WindowManager.class);

    private Window window;
    private WindowEventsManager windowEventsManager;
    private boolean glfwInitialized = false;
    private Vector4f viewport = new Vector4f();
    private Runnable eventPolicyRunner;
    private Runnable hintCallback;

    @Override
    public void init() throws GeodeException {
        if(!glfwInitialized) {
            logger.info("initializing...");
            glfwInitialized = GLFW.glfwInit();
            if(glfwInitialized) {
                GLFWErrorCallback.createPrint(System.err).set();
                logger.info("glfw version: {}", GLFW.glfwGetVersionString());
                glfwWindowHint(GLFW_SAMPLES, 4);
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
                windowEventsManager.init();
                viewport = new Vector4f(0, 0, window.getSize().x, window.getSize().y);
                GL.createCapabilities();
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_MULTISAMPLE);
                glEnable(GL_LINE_SMOOTH);
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

    public Vector4f getViewport() {
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
}

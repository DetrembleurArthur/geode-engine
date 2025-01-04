package com.geode.core;

import com.geode.core.mouse.Button;
import com.geode.core.mouse.ButtonState;
import com.geode.core.mouse.Cursors;
import com.geode.core.reflections.Singleton;
import com.geode.core.mouse.MouseInput;
import com.geode.core.registry.CameraRegistry;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Image;
import com.geode.graphics.camera.Camera2D;
import com.geode.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MathUtil;

import static org.lwjgl.glfw.GLFW.*;

@Singleton
public class MouseManager implements Initializable, Closeable {

    @Override
    public void close() throws Exception {
        destroyCursor();
    }

    private static MouseManager instance;
    private final Window window;
    private final MouseInput[] buttons = new MouseInput[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private long cursor = 0;

    MouseManager(Window window) throws GeodeException {
        if (instance == null)
            instance = this;
        else
            throw new GeodeException("MouseManager is a singleton");
        this.window = window;
    }

    public static MouseManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {

    }

    public Vector2i getPosition() {
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(window.getPointer(), x, y);
        return new Vector2i((int) x[0], (int) y[0]);
    }

    public Vector2i getPosition(Camera2D camera) {
        Vector2i mp = getPosition();
        return Utils.screenToWorld(mp, camera);
    }

    public Vector2i getPositionFromCamera2D() {
        return getPosition(CameraRegistry.getInstance().get(Camera2D.class));
    }

    public void hide() {
        glfwSetInputMode(window.getPointer(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public void lock() {
        glfwSetInputMode(window.getPointer(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void captured() {
        glfwSetInputMode(window.getPointer(), GLFW_CURSOR, GLFW_CURSOR_CAPTURED);
    }

    public void setPosition(Vector2i position) {
        glfwSetCursorPos(window.getPointer(), position.x, position.y);
    }

    public void center() {
        Vector2i pos = window.getSize().div(2);
        glfwSetCursorPos(window.getPointer(), pos.x, pos.y);
    }

    public void sticky(boolean value) {
        glfwSetInputMode(window.getPointer(), GLFW_STICKY_MOUSE_BUTTONS, value ? GLFW_TRUE : GLFW_FALSE);
    }

    public boolean hovered() {
        return glfwGetWindowAttrib(window.getPointer(), GLFW_HOVERED) != 0;
    }

    public int buttonState(Button btn) {
        return glfwGetMouseButton(window.getPointer(), btn.getGlfwId());
    }

    public void normal() {
        glfwSetInputMode(window.getPointer(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public boolean isButton(Button btn, ButtonState state) {
        return buttonState(btn) == state.getGlfwId();
    }

    public boolean isLeftButton(ButtonState state) {
        return isButton(Button.LEFT, state);
    }

    public boolean isRightButton(ButtonState state) {
        return isButton(Button.RIGHT, state);
    }

    public boolean isMiddleButton(ButtonState state) {
        return isButton(Button.MIDDLE, state);
    }

    public boolean isAnyButtonPressed() {
        return isLeftButton(ButtonState.PRESSED) || isRightButton(ButtonState.PRESSED) || isMiddleButton(ButtonState.PRESSED);
    }

    private void destroyCursor() {
        if (this.cursor != 0) {
            glfwDestroyCursor(this.cursor);
            this.cursor = 0;
        }
    }

    public void setCursor(Cursors cursor) {
        destroyCursor();
        if (cursor != Cursors.DEFAULT)
            this.cursor = glfwCreateStandardCursor(cursor.getGlfwId());
        glfwSetCursor(window.getPointer(), this.cursor);
    }

    public void setCursor(Image image) {
        destroyCursor();
        this.cursor = glfwCreateCursor(image.getGlfwImage(), 0, 0);
        glfwSetCursor(window.getPointer(), this.cursor);
    }

    public void setCursor(String imagePath) throws Exception {
        Image image = new Image(imagePath);
        image.init();
        destroyCursor();
        this.cursor = glfwCreateCursor(image.getGlfwImage(), 0, 0);
        glfwSetCursor(window.getPointer(), this.cursor);
        image.close();
    }
}

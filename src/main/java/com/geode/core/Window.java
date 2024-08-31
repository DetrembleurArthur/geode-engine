package com.geode.core;


import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Window implements Closeable {

    private static final Logger logger = LogManager.getLogger(Window.class);

    private long reference = 0;
    private float aspectRatio = 1;
    private Vector4f clearColor = new Vector4f(0, 0, 0, 1);

    public Window(String title, Vector2i size) throws GeodeException {
        reference = GLFW.glfwCreateWindow(size.x, size.y, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(reference != 0) {
            logger.info("Window created");
            aspectRatio = size.x / (float)size.y;
        } else {
            logger.error("Window creation error");
            throw new GeodeException("Window creation error");
        }
    }

    @Override
    public void close() {
        GLFW.glfwMakeContextCurrent(NULL);
        if(reference != 0) {
            GLFW.glfwDestroyWindow(reference);
            logger.info("Window closed");
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void makeCurrent() {
        GLFW.glfwMakeContextCurrent(reference);
    }

    public boolean mustBeClosed() {
        return GLFW.glfwWindowShouldClose(reference);
    }

    public void clear() {
        GL11.glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void swap() {
        GLFW.glfwSwapBuffers(reference);
    }

    public boolean isCreated() {
        return reference != 0;
    }

    public void manually_close() {
        GLFW.glfwSetWindowShouldClose(reference, true);
    }

    public void setMonitor() throws Exception {
        throw new Exception();
    }

    public void setSize(Vector2i size) {
        GLFW.glfwSetWindowSize(reference, size.x, size.y);
    }

    public void setLimits(int minWidth, int minHeight, int maxWidth, int maxHeight) {
        GLFW.glfwSetWindowSizeLimits(reference, minWidth, minHeight, maxWidth, maxHeight);
    }

    public void setAspectRatio(int num, int den) {
        if (num == 0 || den == 0) {
            Vector2i size = getSize();
            num = size.x;
            den = size.y;
        }
        aspectRatio = num / (float) den;
        GLFW.glfwSetWindowAspectRatio(reference, num, den);
    }

    public void resetVpAspectRatio() {
        aspectRatio = getSize().x / (float) getSize().y;
    }

    public void setPosition(Vector2f position) {
        GLFW.glfwSetWindowPos(reference, (int) position.x, (int) position.y);
    }

    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(reference, title);
    }

    public void setOpacity(float opacity) {
        if (opacity < 0)
            opacity = 0;
        else if (opacity > 1.0)
            opacity = 1.0F;
        GLFW.glfwSetWindowOpacity(reference, opacity);
    }

    public Vector2i getSize() {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetWindowSize(reference, width, height);
        return new Vector2i(width[0], height[0]);
    }

    public Vector4f getFrameSize() {
        int[] left = new int[1];
        int[] top = new int[1];
        int[] right = new int[1];
        int[] bottom = new int[1];
        GLFW.glfwGetWindowFrameSize(reference, left, top, right, bottom);
        return new Vector4f(left[0], top[0], right[0], bottom[0]);
    }

    public Vector2i getFrameBufferSize() {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetFramebufferSize(reference, width, height);
        return new Vector2i(width[0], height[0]);
    }

    public Vector2f getContentScale() {
        float[] x = new float[1];
        float[] y = new float[1];
        GLFW.glfwGetWindowContentScale(reference, x, y);
        return new Vector2f(x[0], y[0]);
    }

    public Vector2f getPosition() {
        int[] x = new int[1];
        int[] y = new int[1];
        GLFW.glfwGetWindowPos(reference, x, y);
        return new Vector2f(x[0], y[0]);
    }

    public long getPointer() {
        return reference;
    }

    public String getTitle() {
        return GLFW.glfwGetWindowTitle(reference);
    }

    public float getOpacity() {
        return GLFW.glfwGetWindowOpacity(reference);
    }

    public Object getMonitor() throws Exception {
        throw new Exception();
    }

    public Vector4f getClearColor() {
        return clearColor;
    }

    public boolean isIconified() {
        return GLFW.glfwGetWindowAttrib(reference, GLFW.GLFW_ICONIFIED) == GLFW.GLFW_TRUE;
    }

    public boolean isMaximized() {
        return GLFW.glfwGetWindowAttrib(reference, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
    }

    public boolean isVisible() {
        return GLFW.glfwGetWindowAttrib(reference, GLFW.GLFW_VISIBLE) == GLFW.GLFW_TRUE;
    }

    public boolean isFocused() {
        return GLFW.glfwGetWindowAttrib(reference, GLFW.GLFW_FOCUSED) == GLFW.GLFW_TRUE;
    }

    public boolean isFrameBufferTransparent() {
        return GLFW.glfwGetWindowAttrib(reference, GLFW.GLFW_TRANSPARENT_FRAMEBUFFER) == GLFW.GLFW_TRUE;
    }

    public void iconify() {
        GLFW.glfwIconifyWindow(reference);
    }

    public void restore() {
        GLFW.glfwRestoreWindow(reference);
    }

    public void maximize() {
        GLFW.glfwMaximizeWindow(reference);
    }

    public void fullscreen() throws Exception {
        throw new Exception();
    }

    public void windowed() {
        GLFW.glfwSetWindowMonitor(reference, 0, 0, 0, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
    }

    public void hide() {
        GLFW.glfwHideWindow(reference);
    }

    public void show() {
        GLFW.glfwShowWindow(reference);
    }

    public void focus() {
        GLFW.glfwFocusWindow(reference);
    }

    public void requestAttention() {
        GLFW.glfwRequestWindowAttention(reference);
    }

    public void decorated(boolean value) {
        GLFW.glfwSetWindowAttrib(reference, GLFW.GLFW_DECORATED, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void resizeable(boolean value) {
        GLFW.glfwSetWindowAttrib(reference, GLFW.GLFW_RESIZABLE, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void floated(boolean value) {
        GLFW.glfwSetWindowAttrib(reference, GLFW.GLFW_FLOATING, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void setIcon(String iconPath) throws Exception {
        /*if (iconPath.isEmpty()) {
            GLFW.glfwSetWindowIcon(reference, 0, null);
        } else {
            Image image = new Image(iconPath);
            image.load();
            GLFW.glfwSetWindowIcon(reference, image.getImageBuffer());
        }*/
        throw new Exception();
    }

    public void setClearColor(Vector4f color) {
        clearColor.set(color);
    }


}

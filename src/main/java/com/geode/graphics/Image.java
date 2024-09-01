package com.geode.graphics;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Image implements Initializable, Closeable {

    private final String filename;
    private ByteBuffer image;
    private final Vector2i size = new Vector2i();
    private GLFWImage glfwImage;

    public Image(String filename) throws GeodeException {
        this.filename = filename;
    }

    @Override
    public void init() throws GeodeException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            IntBuffer compBuffer = stack.mallocInt(1);

            image = STBImage.stbi_load(filename, widthBuffer, heightBuffer, compBuffer, 4);
            if(image == null) {
                throw new GeodeException(filename + " image cannot be loaded => " + STBImage.stbi_failure_reason());
            }
            size.x = widthBuffer.get();
            size.y = heightBuffer.get();
            glfwImage = GLFWImage.malloc();
            glfwImage.set(size.x, size.y, image);
        }
    }

    @Override
    public void close() throws Exception {
        if(image != null) {
            STBImage.stbi_image_free(image);
        }
        if(glfwImage != null) {
            glfwImage.close();
        }
    }

    public String getFilename() {
        return filename;
    }

    public ByteBuffer getImage() {
        return image;
    }

    public Vector2i getSize() {
        return size;
    }

    public GLFWImage getGlfwImage() {
        return glfwImage;
    }
}

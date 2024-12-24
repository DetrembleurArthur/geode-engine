package com.geode.graphics;

import com.geode.core.Resource;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.*;

public class Texture implements Resource {

    private int id;
    private final Vector2i size;
    private final ByteBuffer buffer;
    private Image image;
    private int format = GL_RGBA;

    public Texture(Vector2i size, ByteBuffer buffer) throws GeodeException {
        this.size = size;
        this.buffer = buffer;
    }

    public Texture(Vector2i size, ByteBuffer buffer, int format) {
        this.size = size;
        this.buffer = buffer;
        this.format = format;
    }

    public Texture(Image image) throws GeodeException {
        this(image.getSize(), image.getImage());
        this.image = image;
    }

    public Texture(String filename) throws GeodeException {
        this(new Image(filename, true));
    }


    @Override
    public boolean isLoaded() {
        return id != 0;
    }

    @Override
    public void init() throws GeodeException {
        id = GL11.glGenTextures();
        bind();
        GL11.glTexImage2D(GL_TEXTURE_2D, 0,format, size.x, size.y, 0, format, GL11.GL_UNSIGNED_BYTE, buffer);
        initParameters();
        unbind();
    }

    @Override
    public void close() throws Exception {
        if(isLoaded()) {
            glDeleteTextures(id);
            id = 0;
        }
        if(image != null)
            image.close();
    }

    private void initParameters()
    {
        if(format == GL_RGBA) {
            enableRepeat();
            enableNearest();
        }
        else if(format == GL_RED) {
            enableNearest();
            enableClampToEdge();
        }
    }

    private void enableWrapParameter(int param)
    {
        bind();
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, param);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, param);
        unbind();
    }

    private void enableFilterParameter(int param)
    {
        bind();
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, param);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, param);
        unbind();
    }

    public void enableLinear()
    {
        enableFilterParameter(GL_LINEAR);
    }

    public void enableNearest()
    {
        enableFilterParameter(GL_NEAREST);
    }

    public void enableRepeat()
    {
        enableWrapParameter(GL_REPEAT);
    }

    public void enableMirroredRepeat()
    {
        enableWrapParameter(GL14.GL_MIRRORED_REPEAT);
    }

    public void enableClampToEdge()
    {
        enableWrapParameter(GL_CLAMP_TO_EDGE);
    }

    public void enableClampToBorder()
    {
        enableWrapParameter(GL_CLAMP_TO_BORDER);
    }

    public void active()
    {
        glActiveTexture(GL_TEXTURE0);
    }

    public void bind() {
        GL11.glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GL11.glBindTexture(GL_TEXTURE_2D, 0);
    }

    public Vector2i getSize() {
        return new Vector2i(size);
    }
}

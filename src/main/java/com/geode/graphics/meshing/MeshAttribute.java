package com.geode.graphics.meshing;

import org.lwjgl.opengl.GL11;

import java.util.List;

public class MeshAttribute {
    private int elements = 0;
    private int type = GL11.GL_FLOAT;
    private int size = Float.BYTES;

    public MeshAttribute() {
    }

    public MeshAttribute(int elements, int type, int size) {
        this.elements = elements;
        this.type = type;
        this.size = size;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static int calculateTotalSize(List<MeshAttribute> attributes) {
        int size = 0;
        for(MeshAttribute attribute : attributes) {
            size += attribute.getElements() * attribute.getSize();
        }
        return size;
    }
}

package com.geode.graphics.meshing;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class MeshAttribute {
    private int elements = 0;
    private int type = GL11.GL_FLOAT;
    private int size = Float.BYTES;
    private MeshAttributeType attributeType = MeshAttributeType.UNDEFINED;


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

    public int getTotalSize() {
        return elements * size;
    }

    public MeshAttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(MeshAttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public static int calculateTotalSize(List<MeshAttribute> attributes) {
        int size = 0;
        for (MeshAttribute attribute : attributes) {
            size += attribute.getElements() * attribute.getSize();
        }
        return size;
    }

    public static int calculateTotalElements(List<MeshAttribute> attributes) {
        int size = 0;
        for (MeshAttribute attribute : attributes) {
            size += attribute.getElements();
        }
        return size;
    }

    public static MeshAttribute createFloat(int elements) {
        return new MeshAttribute(elements, GL11.GL_FLOAT, Float.BYTES);
    }

    public static MeshAttribute createInt(int elements) {
        return new MeshAttribute(elements, GL11.GL_INT, Integer.BYTES);
    }

    public static MeshAttribute get(ArrayList<MeshAttribute> attributes, MeshAttributeType attributeType) {
        return attributes.stream().filter(meshAttribute -> meshAttribute.getAttributeType().equals(attributeType)).findFirst().orElse(null);
    }

    public static int getOffset(ArrayList<MeshAttribute> attributes, MeshAttributeType attributeType) {
        int offset = 0;
        for(MeshAttribute attribute : attributes) {
            if(attribute.getAttributeType().equals(attributeType)) {
               break;
            }
            offset += attribute.getElements();
        }
        return offset;
    }

    public static int getSize(ArrayList<MeshAttribute> attributes, MeshAttributeType attributeType) {
        int size = 0;
        for(MeshAttribute attribute : attributes) {
            if(attribute.getAttributeType().equals(attributeType)) {
                break;
            }
            size += attribute.getTotalSize();
        }
        return size;
    }
}

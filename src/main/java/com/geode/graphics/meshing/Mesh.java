package com.geode.graphics.meshing;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.exceptions.GeodeException;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class Mesh implements Initializable, Closeable {

    private int vao = 0;
    private int vbo = 0;
    private int ebo = 0;
    private List<MeshAttribute> attributes;
    private int elements = 0;
    private int primitive = GL11.GL_TRIANGLES;
    private boolean dynamic = false;
    private boolean textured = false;
    private int lineWeight = 3;


    public void uploadToGPU() {
        if (lineWeight > 0)
            GL30.glLineWidth(lineWeight);
        bind();
        for (int i = 0; i < attributes.size(); i++) {
            GL30.glEnableVertexAttribArray(i);
        }
        GL30.glDrawElements(primitive, elements, GL11.GL_UNSIGNED_INT, 0);
        unbind();
    }

    @Override
    public void init() throws GeodeException {
        vao = GL30.glGenVertexArrays();
        vbo = GL30.glGenBuffers();
        ebo = GL30.glGenBuffers();
    }

    public void fill(List<MeshAttribute> meshAttributes, float[] vertices, int[] elementsData) {
        bind();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, dynamic ? GL15.GL_DYNAMIC_DRAW : GL15.GL_STATIC_DRAW);
        GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
        GL30.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, elementsData, dynamic ? GL15.GL_DYNAMIC_DRAW : GL15.GL_STATIC_DRAW);
        int vertexSize = MeshAttribute.calculateTotalSize(meshAttributes);
        long offset = 0L;
        for (int i = 0; i < meshAttributes.size(); i++) {
            MeshAttribute attribute = meshAttributes.get(i);
            GL20.glVertexAttribPointer(i, attribute.getElements(), attribute.getType(), false, vertexSize, offset);
            GL20.glEnableVertexAttribArray(i);
            offset += (long) attribute.getElements() * attribute.getSize();
        }
        unbind();
        GL30.glBindVertexArray(0);
        this.attributes = meshAttributes;
        this.elements = elementsData.length;
    }

    @Override
    public void close() throws Exception {
        bind();
        for (int i = 0; i < attributes.size(); i++) {
            GL30.glDisableVertexAttribArray(i);
        }
        unbind();
        if (vao != 0)
            GL30.glDeleteVertexArrays(vao);
        if (vbo != 0)
            GL30.glDeleteBuffers(vbo);
        if (ebo != 0)
            GL30.glDeleteBuffers(ebo);
        vao = vbo = ebo = 0;
    }

    public MeshAttribute getAttribute(MeshAttributeType type) {
        return attributes.stream().filter(meshAttribute -> meshAttribute.getAttributeType().equals(type)).toList().getFirst();
    }

    public void setRectUVs(Vector2f origin, Vector2f size) {
        int vertexSize = MeshAttribute.calculateTotalSize(attributes);
        int skipSize = 0;
        for (MeshAttribute attribute : attributes) {
            if (attribute.getAttributeType().equals(MeshAttributeType.UV)) {
                break;
            }
            skipSize += attribute.getSize() * attribute.getElements();
        }
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        float[] uv = new float[]{
                origin.x, origin.y
        };
        GL30.glBufferSubData(GL15.GL_ARRAY_BUFFER, skipSize, uv);
        uv[1] = origin.y + size.y;
        GL30.glBufferSubData(GL15.GL_ARRAY_BUFFER, vertexSize + skipSize, uv);
        uv[0] = origin.x + size.x;
        GL30.glBufferSubData(GL15.GL_ARRAY_BUFFER, 2L * vertexSize + skipSize, uv);
        uv[1] = origin.y;
        GL30.glBufferSubData(GL15.GL_ARRAY_BUFFER, 3L * vertexSize + skipSize, uv);
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public boolean isReady() {
        return vao != 0 && vbo != 0 && ebo != 0;
    }

    public void bind() {
        GL30.glBindVertexArray(vao);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void asTriangle() {
        primitive = GL11.GL_TRIANGLES;
    }

    public void asTriangleStrip() {
        primitive = GL11.GL_TRIANGLE_STRIP;
    }

    public void asTriangleFan() {
        primitive = GL11.GL_TRIANGLE_FAN;
    }

    public void asPoints() {
        primitive = GL11.GL_POINTS;
    }

    public void asLines() {
        primitive = GL11.GL_LINES;
    }

    public void asLineStrip() {
        primitive = GL11.GL_LINE_STRIP;
    }

    public void asLineLoop() {
        primitive = GL11.GL_LINE_LOOP;
    }

    public int getVao() {
        return vao;
    }

    public int getVbo() {
        return vbo;
    }

    public int getEbo() {
        return ebo;
    }

    public List<MeshAttribute> getAttributes() {
        return attributes;
    }

    public int getElements() {
        return elements;
    }

    public int getPrimitive() {
        return primitive;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public int getLineWeight() {
        return lineWeight;
    }

    public boolean isTextured() {
        return textured;
    }

    public void setTextured(boolean textured) {
        this.textured = textured;
    }

    public void setPrimitive(int primitive) {
        this.primitive = primitive;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public void setLineWeight(int lineWeight) {
        this.lineWeight = lineWeight;
    }


}

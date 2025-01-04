package com.geode.graphics.meshing;

import com.geode.core.Initializable;
import com.geode.exceptions.GeodeException;

import java.util.ArrayList;

public abstract class MeshBuilder {

    protected ArrayList<MeshAttribute> attributes;

    public MeshBuilder() {
    }

    public MeshBuilder(ArrayList<MeshAttribute> attributes) {
        this.attributes = attributes;
    }

    protected abstract float[] allocateVertices(int vertexElements, ArrayList<MeshAttribute> attributes);

    protected abstract int[] buildIndices();

    protected abstract void fillPosition(float[] vertices, int vaoIndex, int vaoSize, int skip);

    protected abstract void fillUv(float[] vertices, int vaoIndex, int vaoSize, int skip);

    protected void fillColor(float[] vertices, int vaoIndex, int vaoSize, int skip) {
        for(int i = vaoIndex; i < vertices.length; i += skip) {
            for(int j = 0; j < vaoSize; j++) {
                vertices[i + j] = 1;
            }
        }
    }

    protected void buildVertices(float[] vertices, ArrayList<MeshAttribute> attributes, int vertexElements, Mesh mesh) {
        int offset = 0;
        this.attributes = attributes;
        for (MeshAttribute attribute : attributes) {
            switch (attribute.getAttributeType()) {
                case POSITION:
                    fillPosition(vertices, offset, attribute.getElements(), vertexElements);
                    break;
                case UV:
                    mesh.setTextured(true);
                    fillUv(vertices, offset, attribute.getElements(), vertexElements);
                    break;
                case COLOR:
                    fillColor(vertices, offset, attribute.getElements(), vertexElements);
                    break;
                default:
            }
            offset += attribute.getElements();
        }
    }

    public Mesh build(ArrayList<MeshAttribute> attributes) throws GeodeException {
        int vertexElements = MeshAttribute.calculateTotalElements(attributes);
        float[] vertices = allocateVertices(vertexElements, attributes);
        int[] indices = buildIndices();
        Mesh mesh = new Mesh();
        buildVertices(vertices, attributes, vertexElements, mesh);
        mesh.init();
        mesh.fill(attributes, vertices, indices);
        return mesh;
    }
}

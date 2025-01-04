package com.geode.graphics.meshing;

import java.util.ArrayList;

public class QuadMeshBuilder extends MeshBuilder {

    @Override
    protected float[] allocateVertices(int vertexElements, ArrayList<MeshAttribute> attributes) {
        return new float[vertexElements * 4];
    }

    @Override
    protected int[] buildIndices() {
        return new int[] {
                0, 1, 2,
                0, 2, 3,
        };
    }

    @Override
    protected void fillPosition(float[] vertices, int vaoIndex, int vaoSize, int skip) {
        vertices[vaoIndex] = 0;
        vertices[vaoIndex + 1] = 0;
        vertices[vaoIndex + 2] = 0;

        vertices[vaoIndex + skip] = 0;
        vertices[vaoIndex + skip + 1] = 1;
        vertices[vaoIndex + skip + 2] = 0;

        vertices[vaoIndex + skip * 2] = 1;
        vertices[vaoIndex + skip * 2 + 1] = 1;
        vertices[vaoIndex + skip * 2 + 2] = 0;

        vertices[vaoIndex + skip * 3] = 1;
        vertices[vaoIndex + skip * 3 + 1] = 0;
        vertices[vaoIndex + skip * 3 + 2] = 0;
    }

    @Override
    protected void fillUv(float[] vertices, int vaoIndex, int vaoSize, int skip) {
        vertices[vaoIndex] = 0;
        vertices[vaoIndex + 1] = 0;

        vertices[vaoIndex + skip] = 0;
        vertices[vaoIndex + skip + 1] = 1;

        vertices[vaoIndex + skip * 2] = 1;
        vertices[vaoIndex + skip * 2 + 1] = 1;

        vertices[vaoIndex + skip * 3] = 1;
        vertices[vaoIndex + skip * 3 + 1] = 0;
    }
}

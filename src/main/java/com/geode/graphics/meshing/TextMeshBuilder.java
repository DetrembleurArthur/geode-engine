package com.geode.graphics.meshing;

import com.geode.entity.ui.TextAlignment;
import com.geode.graphics.ui.text.Font;
import com.geode.graphics.ui.text.Glyph;
import com.geode.utils.Pair;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.ArrayList;

public class TextMeshBuilder extends MeshBuilder {

    private static class GlyphInternalData {
        Vector3f position;
        Glyph glyph;
        char character;
    }

    private final String text;
    private final Font font;
    private final ArrayList<GlyphInternalData> internalData = new ArrayList<>();
    private final int lineSpace;
    private final ArrayList<Pair<Integer, Float>> linesWidths = new ArrayList<>();
    private final Vector2f finalTextTextureSize = new Vector2f();
    private final TextAlignment alignment;
    private float minXPos = 0;

    public TextMeshBuilder(String text, Font font, int lineSpace, TextAlignment alignment, ArrayList<MeshAttribute> attributes) {
        super(attributes);
        this.text = text;
        this.font = font;
        this.lineSpace = lineSpace;
        this.alignment = alignment;
        computeCharacterPositions();
    }

    private void computeCharacterPositions() {
        internalData.clear();
        Vector2i offset = new Vector2i();
        float min_xpos = Float.MAX_VALUE;
        float max_xpos = -Float.MAX_VALUE;
        float min_ypos = Float.MAX_VALUE;
        float max_ypos = -Float.MAX_VALUE;
        float width = -Float.MAX_VALUE;
        float lineHeightMin = Float.MAX_VALUE;
        float lineHeightMax = -Float.MAX_VALUE;
        int i = 0;
        final int vertexElements = MeshAttribute.calculateTotalElements(attributes);
        for (Character character : text.toCharArray()) {
            if (character == '\n') {
                offset.y -= (int) (lineHeightMax - lineHeightMin + lineSpace);
                lineHeightMin = Float.MAX_VALUE;
                lineHeightMax = -Float.MAX_VALUE;
                offset.x = 0;
                linesWidths.add(new Pair<>(i, width));
                width = -Float.MAX_VALUE;
            } else {
                Glyph glyph = font.getGlyphs().get(character);
                float posx = offset.x + glyph.getOffsetX();
                float posy = offset.y - glyph.getOffsetY();
                GlyphInternalData data = new GlyphInternalData();
                data.glyph = glyph;
                data.position = new Vector3f(posx, posy, 0);
                data.character = character;
                internalData.add(data);
                min_xpos = Float.min(min_xpos, posx);
                max_xpos = Float.max(max_xpos, posx + glyph.getWidth());
                min_ypos = Float.min(min_ypos, posy);
                max_ypos = Float.max(max_ypos, posy + glyph.getHeight());
                lineHeightMin = Float.min(lineHeightMin, posy);
                lineHeightMax = Float.max(lineHeightMax, posy + glyph.getOffsetY());
                width = Float.max(width, posx + glyph.getWidth());
                offset.x += (glyph.getAdvance() >> 6);
                i += vertexElements * 4;
            }
        }
        linesWidths.add(new Pair<>(i, width));
        finalTextTextureSize.x = max_xpos - min_xpos;
        finalTextTextureSize.y = max_ypos - min_ypos;
        minXPos = min_xpos;
    }

    private void align(float[] vertices) {
        if (alignment != TextAlignment.LEFT) {
            int lineI = 0;
            for (Pair<Integer, Float> pair : linesWidths) {
                float line_width = (pair.getSecond() - minXPos) / finalTextTextureSize.x;
                float padding_width = 1 - line_width;
                float padding = 0;
                int line_index = pair.getFirst();
                while (lineI < line_index) {
                    padding = switch (alignment) {
                        case TextAlignment.RIGHT -> padding_width;
                        case TextAlignment.CENTER -> (float) (padding_width / 2.0);
                        default -> padding;
                    };
                    vertices[lineI] += padding;
                    lineI += MeshAttribute.calculateTotalElements(attributes);
                }
            }
        }
    }

    @Override
    protected float[] allocateVertices(int vertexElements, ArrayList<MeshAttribute> attributes) {
        MeshAttribute position = attributes.stream()
                .filter(meshAttribute -> meshAttribute.getAttributeType().equals(MeshAttributeType.POSITION))
                .findFirst().orElseThrow();
        MeshAttribute uv = attributes.stream()
                .filter(meshAttribute -> meshAttribute.getAttributeType().equals(MeshAttributeType.UV))
                .findFirst().orElseThrow();
        MeshAttribute color = attributes.stream()
                .filter(meshAttribute -> meshAttribute.getAttributeType().equals(MeshAttributeType.COLOR))
                .findFirst().orElse(null);
        int size = position.getElements() + uv.getElements() + (color == null ? 0 : color.getElements());
        return new float[text.length() * size * 4];
    }

    @Override
    protected int[] buildIndices() {
        int[] indices = new int[text.length() * 6];
        for (int j = 0; j < text.length(); j++) {
            indices[j * 6] = j * 4;
            indices[j * 6 + 1] = j * 4 + 1;
            indices[j * 6 + 2] = j * 4 + 2;
            indices[j * 6 + 3] = j * 4;
            indices[j * 6 + 4] = j * 4 + 2;
            indices[j * 6 + 5] = j * 4 + 3;
        }
        return indices;
    }

    @Override
    protected void fillPosition(float[] vertices, int vaoIndex, int vaoSize, int skip) {
        for (GlyphInternalData data : internalData) {
            vertices[vaoIndex] = data.position.x / finalTextTextureSize.x;
            vertices[vaoIndex + 1] = data.position.y / finalTextTextureSize.y + 1;
            vertices[vaoIndex + 2] = 0;

            vertices[vaoIndex + skip] = data.position.x / finalTextTextureSize.x;
            vertices[vaoIndex + skip + 1] = (data.position.y + data.glyph.getHeight()) / finalTextTextureSize.y + 1;
            vertices[vaoIndex + skip + 2] = 0;

            vertices[vaoIndex + skip * 2] = (data.position.x + data.glyph.getWidth()) / finalTextTextureSize.x;
            vertices[vaoIndex + skip * 2 + 1] = (data.position.y + data.glyph.getHeight()) / finalTextTextureSize.y + 1;
            vertices[vaoIndex + skip * 2 + 2] = 0;

            vertices[vaoIndex + skip * 3] = (data.position.x + data.glyph.getWidth()) / finalTextTextureSize.x;
            vertices[vaoIndex + skip * 3 + 1] = data.position.y / finalTextTextureSize.y + 1;
            vertices[vaoIndex + skip * 3 + 2] = 0;
            vaoIndex += skip * 4;
        }
        align(vertices);
    }

    @Override
    protected void fillUv(float[] vertices, int vaoIndex, int vaoSize, int skip) {
        Vector2i size = font.getTexture().getSize();
        for (GlyphInternalData data : internalData) {
            vertices[vaoIndex] = data.glyph.getTextureOffset() / (float) size.x;
            vertices[vaoIndex + 1] = 0;

            vertices[vaoIndex + skip] = data.glyph.getTextureOffset() / (float) size.x;
            vertices[vaoIndex + skip + 1] = data.glyph.getHeight() / (float) size.y;

            vertices[vaoIndex + skip * 2] = data.glyph.getTextureOffset() / (float) size.x + data.glyph.getWidth() / (float) size.x;
            vertices[vaoIndex + skip * 2 + 1] = data.glyph.getHeight() / (float) size.y;

            vertices[vaoIndex + skip * 3] = data.glyph.getTextureOffset() / (float) size.x + data.glyph.getWidth() / (float) size.x;
            vertices[vaoIndex + skip * 3 + 1] = 0;
            vaoIndex += skip * 4;
        }
    }

    public Vector2f getFinalTextTextureSize() {
        return finalTextTextureSize;
    }
}

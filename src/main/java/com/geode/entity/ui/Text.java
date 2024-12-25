package com.geode.entity.ui;

import com.geode.core.components.render.RendererComponent;
import com.geode.entity.SpacialGameObject;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.meshing.MeshAttribute;
import com.geode.graphics.meshing.MeshAttributeType;
import com.geode.graphics.renderer.FontRenderer;
import com.geode.graphics.ui.text.Font;
import com.geode.graphics.ui.text.Glyph;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Text extends SpacialGameObject {
    private String value;
    private int lineSpace = 0;
    private int maxChars = 100;
    private final Font font;
    private int stickyIndex = 0;

    public Text(String text, Font font, FontRenderer renderer) throws GeodeException {
        assignDefaultRenderer(renderer, font.getTexture());
        this.font = font;
        setValue(text);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        try {
            RendererComponent renderer = getComponent(RendererComponent.class);
            int positionSize = renderer.getMesh().getAttribute(MeshAttributeType.POSITION).getElements();
            int uvSize = renderer.getMesh().getAttribute(MeshAttributeType.UV).getElements();
            final int verticesByGlyph = 4;
            final int indicesByGlyph = 6;
            int globalSize = value.length() * (positionSize + uvSize) * verticesByGlyph;
            float[] vertices = new float[globalSize];
            int[] indices = new int[value.length() * indicesByGlyph];
            final Vector2i texSize = renderer.getTexture().getSize();
            Vector2i offset = new Vector2i();
            float min_xpos = Float.MAX_VALUE;
            float max_xpos = Float.MIN_VALUE;
            float min_ypos = Float.MAX_VALUE;
            float max_ypos = Float.MIN_VALUE;
            float width = Float.MIN_VALUE;
            int i = 0, j = 0;
            for (Character character : value.toCharArray()) {
                Glyph glyph = font.getGlyphs().get(character);
                float posx = offset.x + glyph.getOffsetX();
                float posy = offset.y - glyph.getOffsetY();
                min_xpos = Float.min(min_xpos, posx);
                max_xpos = Float.max(max_xpos, posx + glyph.getWidth());
                min_ypos = Float.min(min_ypos, posy);
                max_ypos = Float.max(max_ypos, posy + glyph.getHeight());
                width = Float.max(width, posx + glyph.getWidth());
                vertices[i++] = posx;
                vertices[i++] = posy;
                vertices[i++] = 0;
                vertices[i++] = glyph.getTextureOffset() / (float) texSize.x;
                vertices[i++] = 0;

                vertices[i++] = posx;
                vertices[i++] = posy + glyph.getHeight();
                vertices[i++] = 0;
                vertices[i++] = glyph.getTextureOffset() / (float) texSize.x;
                vertices[i++] = glyph.getHeight() / (float) texSize.y;

                vertices[i++] = posx + glyph.getWidth();
                vertices[i++] = posy + glyph.getHeight();
                vertices[i++] = 0;
                vertices[i++] = glyph.getTextureOffset() / (float) texSize.x + glyph.getWidth() / (float) texSize.x;
                vertices[i++] = glyph.getHeight() / (float) texSize.y;

                vertices[i++] = posx + glyph.getWidth();
                vertices[i++] = posy;
                vertices[i++] = 0;
                vertices[i++] = glyph.getTextureOffset() / (float) texSize.x + glyph.getWidth() / (float) texSize.x;
                vertices[i++] = 0;

                indices[j * indicesByGlyph] = j * verticesByGlyph;
                indices[j * indicesByGlyph + 1] = j * verticesByGlyph + 1;
                indices[j * indicesByGlyph + 2] = j * verticesByGlyph + 2;
                indices[j * indicesByGlyph + 3] = j * verticesByGlyph;
                indices[j * indicesByGlyph + 4] = j * verticesByGlyph + 2;
                indices[j * indicesByGlyph + 5] = j * verticesByGlyph + 3;
                j++;

                offset.x += (glyph.getAdvance() >> 6);
            }
            Vector2f maxTextSize = new Vector2f();
            maxTextSize.x = max_xpos - min_xpos;
            maxTextSize.y = max_ypos - min_ypos;
            for (i = 0; i < globalSize; i += (positionSize + uvSize)) {
                // normalisation
                vertices[i] /= maxTextSize.x;
                //vertices[i+1] += max_text_size.y;
                vertices[i + 1] /= maxTextSize.y;
                vertices[i + 1] += 1;
            }

            Vector3f oldSize = new Vector3f(tr().getSize());
            tr().setSize(new Vector3f(maxTextSize.x, maxTextSize.y, oldSize.z));
            if (stickyIndex == 0)
                setTextWidth(oldSize.x);
            else if (stickyIndex == 1)
                setTextHeight(oldSize.y);
            var mesh = new Mesh();
            mesh.init();
            mesh.setDynamic(false);
            mesh.setTextured(true);
            mesh.fill(renderer.getRenderer().getShader().getMeshAttributes(), vertices, indices);
            renderer.setMesh(mesh);
        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
        this.value = value;
    }


    public void setTextWidth(float width) {
        Vector3f size = new Vector3f(tr().getSize());
        tr().setSize(new Vector3f(width, width * (size.y / size.x), size.z));
        stickyIndex = 0;
    }

    public void setTextHeight(float height) {
        Vector3f size = new Vector3f(tr().getSize());
        tr().setSize(new Vector3f(height * (size.x / size.y), height, size.z));
        stickyIndex = 1;
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }
}

package com.geode.entity.ui;

import com.geode.core.components.RendererComponent;
import com.geode.core.registry.RendererRegistry;
import com.geode.entity.SpacialGameObject;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.meshing.MeshAttribute;
import com.geode.graphics.meshing.MeshAttributeType;
import com.geode.graphics.meshing.TextMeshBuilder;
import com.geode.graphics.renderer.FontRenderer;
import com.geode.graphics.ui.text.Font;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;

public class Text extends SpacialGameObject {
    private String value;
    private int lineSpace = 5;
    private int maxChars = 1000;
    private final Font font;
    private int stickyIndex = 0;
    private TextAlignment alignment = TextAlignment.LEFT;
    private ArrayList<TextCharacterAttributes> textCharacterAttributes;

    public Text(String text, Font font) {
        this(text, font, RendererRegistry.getInstance().get(FontRenderer.class));
    }

    public Text(String text, Font font, FontRenderer renderer) {
        this.font = font;
        getComponent(RendererComponent.class).setRenderer(renderer);
        getComponent(RendererComponent.class).setTexture(font.getTexture());
        setValue(text);
    }

    private void createCharacterAttributes() {
        textCharacterAttributes = new ArrayList<>();
    }

    private RendererComponent enableRichColor() {
        RendererComponent component = getComponent(RendererComponent.class);
        FontRenderer renderer = (FontRenderer) component.getRenderer();
        if (!renderer.isColorized()) {
            renderer.setAsColorized();
            createCharacterAttributes();
            setValue(value);
            for (int i = 0; i < value.length(); i++) {
                textCharacterAttributes.add(new TextCharacterAttributes());
            }
        }
        return component;
    }

    public void setCharacterColor(int charIndex, Vector4f color) {
        if (charIndex < value.length()) {
            RendererComponent component = enableRichColor();
            FontRenderer renderer = (FontRenderer) component.getRenderer();
            Mesh mesh = component.getMesh();
            ArrayList<MeshAttribute> attributes = renderer.getShader().getMeshAttributes();
            float[] vecColor = new float[]{color.x, color.y, color.z, color.w};
            TextCharacterAttributes attribute = textCharacterAttributes.get(charIndex);
            attribute.setRed(color.x);
            attribute.setGreen(color.y);
            attribute.setBlue(color.z);
            attribute.setAlpha(color.w);
            mesh.updateFaceVertices(attributes, MeshAttributeType.COLOR, charIndex, vecColor);
        }
    }

    public void setCharacterColors(int charIndex, Vector4f[] colors) {
        if (charIndex < value.length()) {
            RendererComponent component = enableRichColor();
            FontRenderer renderer = (FontRenderer) component.getRenderer();
            Mesh mesh = component.getMesh();
            ArrayList<MeshAttribute> attributes = renderer.getShader().getMeshAttributes();
            float[][] vecColor = new float[4][4];
            for (int i = 0; i < 4; i++) {
                System.arraycopy(new float[]{colors[i].x, colors[i].y, colors[i].z, colors[i].w}, 0, vecColor[i], 0, 4);
            }
            mesh.updateFaceVertices(attributes, MeshAttributeType.COLOR, charIndex, vecColor);
        }
    }

    public String getValue() {
        return value;
    }

    private String preprocessValue(String value) {
        if (value.length() > maxChars) {
            value = value.substring(0, maxChars - 1);
        }
        if (value.contains("\n")) {
            String[] lines = value.split("\n");
            StringBuilder localValueBuilder = new StringBuilder();
            for (int i = lines.length - 1; i >= 0; i--) {
                localValueBuilder.append(lines[i]).append("\n");
            }
            value = localValueBuilder.toString();
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private void updateCharacterAttributes() {
        int i = 0;
        for (TextCharacterAttributes characterAttributes : textCharacterAttributes) {
            setCharacterColor(i, characterAttributes.getColor());
            i++;
        }
    }

    public void setValue(String value) {
        String localValue = preprocessValue(value);
        try {
            RendererComponent rendererComponent = getComponent(RendererComponent.class);
            TextMeshBuilder builder = new TextMeshBuilder(localValue, font, lineSpace, alignment, rendererComponent.getRenderer().getShader().getMeshAttributes());
            final Vector2f finalTextureSize = builder.getFinalTextTextureSize();
            Vector3f oldSize = new Vector3f(tr().getSize());
            tr().setSize(new Vector3f(finalTextureSize.x, finalTextureSize.y, oldSize.z));
            if (stickyIndex == 0)
                setTextWidth(oldSize.x);
            else if (stickyIndex == 1)
                setTextHeight(oldSize.y);
            var mesh = builder.build(rendererComponent.getRenderer().getShader().getMeshAttributes());
            rendererComponent.setMesh(mesh);
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
        setValue(value);
    }

    public int getMaxChars() {
        return maxChars;
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
        setValue(value);
    }

    public TextAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
        setValue(value);
        if (textCharacterAttributes != null)
            updateCharacterAttributes();
    }

    public int getStickyIndex() {
        return stickyIndex;
    }

    public void setStickyIndex(int stickyIndex) {
        this.stickyIndex = stickyIndex;
        setValue(value);
        if (textCharacterAttributes != null)
            updateCharacterAttributes();
    }
}

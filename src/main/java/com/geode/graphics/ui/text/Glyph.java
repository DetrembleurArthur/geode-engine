package com.geode.graphics.ui.text;

import java.nio.ByteBuffer;

public class Glyph {
    int width;
    int height;
    int advance;
    int offsetX;
    int offsetY;
    int textureOffset;
    ByteBuffer bitmap;

    public Glyph(int width, int height, int advance, int offsetX, int offsetY, int textureOffset) {
        this.width = width;
        this.height = height;
        this.advance = advance;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.textureOffset = textureOffset;
    }

    public void setBitmap(ByteBuffer bitmap) {
        this.bitmap = bitmap;
    }

    public ByteBuffer getBitmap() {
        return bitmap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getAdvance() {
        return advance;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getTextureOffset() {
        return textureOffset;
    }
}
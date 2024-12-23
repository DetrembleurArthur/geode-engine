package com.geode.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SpriteSheetConfiguration implements Serializable {
    private int width;
    private int height;
    private int rows;
    private int columns;
    private int row_padding = 0;
    private int column_padding = 0;
    private HashMap<String, AnimationFrames> animations = new HashMap<>();

    public static class AnimationFrames implements Serializable {
        private int start;
        private int end;

        public AnimationFrames() {
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    public SpriteSheetConfiguration() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRow_padding() {
        return row_padding;
    }

    public void setRow_padding(int row_padding) {
        this.row_padding = row_padding;
    }

    public int getColumn_padding() {
        return column_padding;
    }

    public void setColumn_padding(int column_padding) {
        this.column_padding = column_padding;
    }

    public HashMap<String, AnimationFrames> getAnimations() {
        return animations;
    }

    public void setAnimations(HashMap<String, AnimationFrames> animations) {
        this.animations = animations;
    }
}

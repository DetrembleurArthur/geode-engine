package com.geode.graphics.sprite;

import java.util.ArrayList;

public class SpriteAnimation {
    private final ArrayList<Sprite> sprites = new ArrayList<>();
    private int currentIndex = 0;

    public Sprite get() {
        return sprites.get(currentIndex);
    }

    public void next() {
        currentIndex = (currentIndex + 1) % sprites.size();
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void reset() {
        currentIndex = 0;
    }
}

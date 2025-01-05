package com.geode.core.components;

import com.geode.core.components.base.UpdateableComponent;
import com.geode.core.time.ActionTimer;
import com.geode.entity.GameObject;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.sprite.Sprite;
import com.geode.graphics.sprite.SpriteAnimation;
import com.geode.graphics.sprite.SpriteSheet;

public class SpriteComponent extends UpdateableComponent {

    private SpriteSheet spriteSheet;
    private SpriteAnimation currentAnimation;
    private final ActionTimer timer;

    public SpriteComponent(GameObject child) {
        super(child);
        timer = new ActionTimer(1.0);
    }

    @Override
    public void init() throws GeodeException {
        timer.setAutoRestart(true);
        timer.setStartCondition(() -> spriteSheet != null && currentAnimation != null);
        timer.setStartConditionAsRestartCondition();
        timer.setOnStop(() -> {
            currentAnimation.next();
            updateUvs();
        });
    }

    @Override
    public void update() {
        timer.update();
    }

    @Override
    public void close() throws Exception {

    }

    private void updateUvs() {
        Sprite sprite = currentAnimation.get();
        Mesh mesh = getChild().getComponent(RendererComponent.class).getMesh();
        mesh.setRectUVs(sprite.getPosition(), sprite.getSize());
    }

    public SpriteComponent setAnimationDelay(double sec) {
        timer.setDelay(sec);
        return this;
    }

    public SpriteComponent setAnimation(String name) {
        currentAnimation = spriteSheet.getAnimation(name);
        updateUvs();
        return this;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public SpriteComponent setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        getChild().getComponent(RendererComponent.class).setTexture(spriteSheet.getTexture());
        return this;
    }
}

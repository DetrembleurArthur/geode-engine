package com.geode.graphics.sprite;

import com.geode.config.JsonLoader;
import com.geode.config.SpriteSheetConfiguration;
import com.geode.core.Resource;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.io.IOException;
import java.util.HashMap;

public class SpriteSheet implements Resource {

    private static final Logger logger = LogManager.getLogger(SpriteSheet.class);

    private final Vector2i size;
    private final int rows;
    private final int cols;
    private final Vector2i padding;
    private int spriteNumber = 0;
    private Vector2i spriteSize;
    private Vector2f spriteSizeNormalized;
    private final HashMap<String, SpriteAnimation> animations = new HashMap<>();
    private int innerInitialRowCounter = 0;

    public SpriteSheet(String configurationFile) throws IOException {
        SpriteSheetConfiguration configuration = JsonLoader.load(configurationFile, SpriteSheetConfiguration.class);
        size = new Vector2i(configuration.getWidth(), configuration.getHeight());
        rows = configuration.getRows();
        cols = configuration.getColumns();
        padding = new Vector2i(configuration.getColumn_padding(), configuration.getRow_padding());
        spriteNumber = rows * cols;
        spriteSize = new Vector2i(size.x / cols - padding.x, size.y / rows - padding.y);
        spriteSizeNormalized = new Vector2f((float) spriteSize.x / size.x, (float) spriteSize.y / size.x);
        configuration.getAnimations().forEach((name, frames) -> registerAnimation(name, frames.getStart(), frames.getEnd()));
    }

    public SpriteSheet(final Texture texture, int rows, int cols, Vector2i padding) throws GeodeException {
        this.size = texture.getSize();
        this.rows = rows;
        this.cols = cols;
        this.padding = new Vector2i(padding);
        spriteNumber = rows * cols;
        spriteSize = new Vector2i(size.x / cols - padding.x, size.y / rows - padding.y);
        spriteSizeNormalized = new Vector2f((float) spriteSize.x / size.x, (float) spriteSize.y / size.x);
    }

    public SpriteSheet(final Texture texture, int rows, int cols) throws GeodeException {
        this(texture, rows, cols, new Vector2i());
    }

    public SpriteSheet registerAnimation(String name, int startSpriteId, int endSpriteId) {
        SpriteAnimation animation = new SpriteAnimation();
        for (int i = startSpriteId; i <= endSpriteId; i++) {
            int col = i % cols;
            int row = i / rows;
            Vector2f position = getPositionFromCoordinates(row, col);
            animation.addSprite(new Sprite(position, spriteSizeNormalized));
        }
        animations.put(name, animation);
        return this;
    }

    public SpriteSheet registerAnimation(String name, int rowId) {
        SpriteAnimation animation = new SpriteAnimation();
        for (int col = 0; col < cols; col++) {
            Vector2f position = getPositionFromCoordinates(rowId, col);
            animation.addSprite(new Sprite(position, spriteSizeNormalized));
        }
        animations.put(name, animation);
        return this;
    }

    public SpriteSheet registerAnimation(String name) {
        return registerAnimation(name, innerInitialRowCounter++);
    }

    private Vector2f getPositionFromCoordinates(int rowId, int colId) {
        return new Vector2f((float) (spriteSize.x * colId + colId * padding.x) / size.x, (float) (spriteSize.y * rowId + rowId * padding.y) / size.x);
    }

    public SpriteAnimation getAnimation(String name) {
        return animations.get(name);
    }

    @Override
    public boolean isLoaded() {
        return spriteNumber > 0;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void init() throws GeodeException {
        logger.info(
                "Texture size: {}, Rows: {}, Columns: {}, Padding: {}, Number of sprites: {}, Sprite size: {}, Normalized sprite size: {}",
                this.size, this.rows, this.cols, this.padding, this.spriteNumber, this.spriteSize, this.spriteSizeNormalized
        );
    }
}

package com.geode.core;

import com.geode.entity.GameObjectManager;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera2D;
import com.geode.graphics.renderer.FontRenderer;
import com.geode.graphics.renderer.ShapeRenderer;
import com.geode.graphics.renderer.TextureRenderer;

public abstract class Scene implements Initializable, Closeable, Taggable {

    protected final GameObjectManager goManager = new GameObjectManager();
    protected Camera2D default2DCamera;
    protected TextureRenderer textureRenderer;
    protected ShapeRenderer shapeRenderer;
    protected FontRenderer fontRenderer;

    public final void innerInit() throws GeodeException {
        default2DCamera = new Camera2D(WindowManager.getInstance());
        goManager.init();
        textureRenderer = new TextureRenderer(default2DCamera);
        shapeRenderer = new ShapeRenderer(default2DCamera);
        fontRenderer = new FontRenderer(default2DCamera);
    }

    public final void innerClose() throws Exception {
        goManager.close();
    }

    // appelée à l'instanciation
    public abstract void init();

    // appelée lors d'une dé-pause
    public void resume() throws GeodeException {

    }

    // appelée lors d'une pause
    public void pause() throws GeodeException {

    }

    // appelée lorsque la scene est mise en "courante"
    public void select() throws GeodeException {

    }

    // appelée lorsque la scene n'est plus courante
    public void unselect() throws GeodeException {

    }

    public abstract void update(double dt);

    public void innerUpdate() {
        goManager.update();
    }

    public void draw(double dt) throws GeodeException {
        goManager.draw();
    }

    @Override
    public Object tag() {
        return this;
    }

    public Camera2D getDefault2DCamera() {
        return default2DCamera;
    }

    public GameObjectManager getGoManager() {
        return goManager;
    }

    public TextureRenderer getTextureRenderer() {
        return textureRenderer;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }
}

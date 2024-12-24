package com.geode.core;

import com.geode.entity.GameObjectManager;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Shader;
import com.geode.graphics.camera.Camera2D;
import com.geode.graphics.renderer.Renderer;

public abstract class Scene implements Initializable, Closeable, Taggable {

    protected final GameObjectManager goManager = new GameObjectManager();
    protected Camera2D default2DCamera;
    protected Shader default2DShader;
    protected Renderer<Camera2D> default2DRenderer;
    protected Shader defaultFontShader;
    protected Renderer<Camera2D> defaultFontRenderer;

    public final void innerInit() throws GeodeException {
        default2DCamera = new Camera2D(WindowManager.getInstance());
        goManager.init();
        default2DShader = ShaderManager.getInstance().getResource("default.tex");
        default2DRenderer = new Renderer<>(default2DCamera, default2DShader);
        defaultFontShader = ShaderManager.getInstance().getResource("default.font");
        defaultFontRenderer = new Renderer<>(default2DCamera, defaultFontShader);
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

    public Shader getDefault2DShader() {
        return default2DShader;
    }

    public Renderer<Camera2D> getDefault2DRenderer() {
        return default2DRenderer;
    }

    public GameObjectManager getGoManager() {
        return goManager;
    }

    public Shader getDefaultFontShader() {
        return defaultFontShader;
    }

    public Renderer<Camera2D> getDefaultFontRenderer() {
        return defaultFontRenderer;
    }
}

package com.geode.graphics.camera;

import com.geode.core.WindowManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4i;

public class Camera2D extends Camera {

    private static final Logger logger = LogManager.getLogger(Camera2D.class);

    private Vector4i ortho = new Vector4i();
    private Vector3f zoom = new Vector3f(1, 1, 1);
    private Vector2f position = new Vector2f();
    private float rotation = 0;

    public Camera2D(WindowManager windowManager) {
        adaptOnResize(windowManager);
        logger.info("Camera2D setup: " + ortho);
    }

    @Override
    public void adaptOnResize(WindowManager windowManager) {
        ortho = new Vector4i(windowManager.getViewport());
    }

    @Override
    public Matrix4f updateView() {
        view = view.identity();
        view = view.lookAt(
                new Vector3f(position.x, position.y, 20),
                new Vector3f(position.x, position.y, -1.0f),
                new Vector3f(0.0f, 1.0f, 0.0f));
        Vector3f orthoAdaptation = new Vector3f(position.x + ortho.z / 2.0f, position.y + ortho.w /2.0f, 0);
        view = view.translate(orthoAdaptation);
        view = view.scale(zoom);
        view = view.rotate((float) Math.toRadians(rotation), new Vector3f(0f, 0f, 1f));
        view = view.translate(orthoAdaptation.negate());
        return view;
    }

    @Override
    public Matrix4f updateProjection() {
        projection = projection.identity();
        projection = projection.ortho(ortho.x, ortho.z, ortho.w, ortho.y, 0f, 100f);
        return projection;
    }

    public Vector2f focus(Vector2f target) {
        Vector2f old = position;
        position = target.sub(ortho.z / 2f, ortho.w / 2f);
        return position.sub(old);
    }

    public Vector4i getOrtho() {
        return ortho;
    }

    public void setOrtho(Vector4i ortho) {
        this.ortho = ortho;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector3f getZoom() {
        return zoom;
    }

    public void setZoom(Vector3f zoom) {
        this.zoom = zoom;
    }
}

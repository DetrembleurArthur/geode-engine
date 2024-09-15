package com.geode.graphics.camera;

import com.geode.core.WindowManager;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {

    protected Matrix4f view = new Matrix4f();
    protected Matrix4f projection = new Matrix4f();

    public abstract Matrix4f updateProjection();

    public abstract Matrix4f updateView();

    public abstract void adaptOnResize(WindowManager windowManager);

    public Matrix4f getView() {
        return view;
    }

    public void setView(Matrix4f view) {
        this.view = view;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }
}

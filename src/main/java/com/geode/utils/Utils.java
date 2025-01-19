package com.geode.utils;

import com.geode.core.Application;
import com.geode.graphics.camera.Camera2D;
import org.joml.*;

public class Utils {
    public static Vector2i screenToWorld(Vector2i screen, Camera2D camera2D) {
        Vector4i viewport = Application.getInstance().getWindowManager().getViewport();
        Vector2f position = new Vector2f(screen.sub(viewport.x, viewport.y));

        Vector2f norm = position.div(viewport.z, viewport.w);
        norm = norm.mul(2f, 2f).sub(1f, 1f);

        Vector4f worldPos = new Vector4f(norm.x, -norm.y, 0f, 1f);
        worldPos = worldPos.mul(new Matrix4f(camera2D.getProjection()).invert()).mul(new Matrix4f(camera2D.getView()).invert());
        return new Vector2i((int)worldPos.x, (int)worldPos.y);
    }
}

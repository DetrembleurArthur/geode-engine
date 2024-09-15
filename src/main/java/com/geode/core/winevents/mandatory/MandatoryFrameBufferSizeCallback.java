package com.geode.core.winevents.mandatory;

import com.geode.core.Application;
import com.geode.core.Window;
import com.geode.core.WindowManager;
import com.geode.core.winevents.WinEvents;
import org.joml.Vector2i;
import org.joml.Vector4i;

import static org.lwjgl.opengl.GL11.glViewport;

public class MandatoryFrameBufferSizeCallback implements WinEvents.FbSizeCallback {

    private final WindowManager windowManager;
    private final Application application;

    public MandatoryFrameBufferSizeCallback(WindowManager windowManager, Application application) {
        this.windowManager = windowManager;
        this.application = application;
    }

    @Override
    public void trigger(Vector2i size) {
        Window window = windowManager.getWindow();
        int aspect_width = size.x;
        int aspect_height = (int) (aspect_width / window.getAspectRatio());
        if(aspect_height > size.y)
        {
            aspect_height = size.y;
            aspect_width = (int) (aspect_height * window.getAspectRatio());
        }
        int vpx = (int) (size.x / 2.0 - aspect_width / 2.0);
        int vpy = (int) (size.y / 2.0 - aspect_height / 2.0);
        windowManager.setViewport(new Vector4i(vpx, vpy, aspect_width, aspect_height));
        application.loop(windowManager.getWindow());
    }

    @Override
    public Object tag() {
        return this;
    }
}

package com.geode.core;

import com.geode.core.reflections.Extensions;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import com.geode.graphics.ui.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.freetype.FreeType;

@Singleton
public class FontManager extends ResourceManager<Font> {

    private static final Logger logger = LogManager.getLogger(FontManager.class);
    private static FontManager instance;
    private static long ftLibrary;


    FontManager() throws GeodeException {
        super("font", Font.class, Extensions.FONT_TTF);
        if (instance == null)
            instance = this;
        else
            throw new GeodeException("font manager is a singleton");
    }

    public static FontManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer ftLibraryPtr = stack.mallocPointer(1);
            int error = FreeType.FT_Init_FreeType(ftLibraryPtr);
            if (error != 0) {
                logger.error("Font library initialization failed with error code: {}", error);
                return;
            }
            ftLibrary = ftLibraryPtr.get();
            logger.info("Font library initialization success.");
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (ftLibrary != 0) {
            FreeType.FT_Done_FreeType(ftLibrary);
            ftLibrary = 0;
            logger.info("Font library done.");
        }
    }

    public static long getFtLibrary() {
        return ftLibrary;
    }
}

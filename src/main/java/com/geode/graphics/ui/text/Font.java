package com.geode.graphics.ui.text;

import com.geode.core.FontManager;
import com.geode.core.Resource;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.Texture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FreeType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class Font implements Resource {
    private static final Logger logger = LogManager.getLogger(Font.class);
    private static final long ftLibrary = FontManager.getFtLibrary();
    private FT_Face face;
    private final String filename;
    private int fontSize = 12;
    private String charset = FontCharsets.ascii();
    private final Map<Character, Glyph> glyphs = new HashMap<>();
    private Texture texture;

    public Font(String filename) {
        this.filename = filename;
    }

    public Font(String filename, int fontSize, String charset) {
        this.filename = filename;
        this.fontSize = fontSize;
        this.charset = charset;
    }

    @Override
    public void configure(Object... parameters) {
        if (parameters.length >= 1) {
            fontSize = (int) parameters[0];
            logger.info("configure {} fontSize as {}", filename, fontSize);
        }
        if (parameters.length >= 2) {
            charset = (String) parameters[1];
            logger.info("configure {} charset as {}", filename, charset);
        }
    }

    @Override
    public boolean isLoaded() {
        return texture != null;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
        int error = FreeType.FT_Set_Pixel_Sizes(face, 0, size);
        if (error != 0) {
            logger.error("Cannot set font size to {} with error code: {}", size, error);
        }
    }

    public Map<Character, Glyph> getGlyphs() {
        return glyphs;
    }

    public Texture getTexture() {
        return texture;
    }


    @Override
    public void close() throws Exception {
        if (texture != null) {
            try {
                texture.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            texture = null;
        }
        if (face != null) {
            FreeType.FT_Done_Face(face);
            face = null;
        }
        if(!glyphs.isEmpty()) {
            glyphs.forEach((character, glyph) -> glyph.clear());
            glyphs.clear();
        }
    }

    @Override
    public void init() throws GeodeException {
        try {
            close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ByteBuffer fontBuffer;
        try {
            Path path = Path.of(filename);
            fontBuffer = MemoryUtil.memAlloc(Files.readAllBytes(path).length);
            fontBuffer.put(Files.readAllBytes(path)).flip();
        } catch (IOException e) {
            logger.error("Failed to load font file: {}", e.getMessage());
            return;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer facePtr = stack.mallocPointer(1);
            int error = FreeType.FT_New_Memory_Face(ftLibrary, fontBuffer, 0, facePtr);
            if (error != 0) {
                logger.error("Failed to create new face with error code: {}", error);
                return;
            }
            face = FT_Face.create(facePtr.get());
        }

        setFontSize(fontSize);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        int padding = 10;
        int maxWidth = 0;
        int maxHeight = 0;
        int xOffset = 0;

        for (char c : charset.toCharArray()) {
            int error = FreeType.FT_Load_Char(face, c, FreeType.FT_LOAD_RENDER);
            if (error != 0) {
                logger.error("Cannot load character: {} with error code: {}", c, error);
                continue;
            }

            int width = face.glyph().bitmap().width();
            int height = face.glyph().bitmap().rows();
            int bitmapLeft = face.glyph().bitmap_left();
            int bitmapTop = face.glyph().bitmap_top();
            int advance = (int) face.glyph().advance().x();
            ByteBuffer buffer = null;
            if(width * height != 0) {
                buffer = MemoryUtil.memAlloc(width * height);
                buffer.put(face.glyph().bitmap().buffer(width * height)).flip();
            }
            Glyph glyph = new Glyph(width, height, advance, bitmapLeft, bitmapTop, xOffset);
            glyph.setBitmap(buffer);
            glyphs.put(c, glyph);

            xOffset += width + padding;
            maxWidth = Math.max(maxWidth, xOffset);
            maxHeight = Math.max(maxHeight, height);
        }

        generateTexture(maxWidth, maxHeight);
    }

    private void generateTexture(int width, int height) {
        ByteBuffer textureBuffer = MemoryUtil.memAlloc(width * height);
        MemoryUtil.memSet(textureBuffer, (byte) 0);

        int xOffset = 0;
        for (char c : charset.toCharArray()) {
            Glyph glyph = glyphs.get(c);
            ByteBuffer bitmap = glyph.getBitmap();
            if(bitmap != null) {
                for (int y = 0; y < glyph.height; y++) {
                    for (int x = 0; x < glyph.width; x++) {
                        int index = xOffset + x + (y * width);
                        textureBuffer.put(index, bitmap.get(x + y * glyph.width));
                    }
                }
            }
            xOffset += glyph.width + 10;
        }

        try {
            texture = new Texture(new Vector2i(width, height), textureBuffer, GL_RED);
            texture.load();
        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }
        MemoryUtil.memFree(textureBuffer);
        logger.info("Font '{}' loaded.", filename);
    }
}

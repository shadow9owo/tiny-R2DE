package game.Core.Wrappers.Texture;

import game.Core.Types.Texture_t;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private static ByteBuffer readResourceToBuffer(String path) {
        try (InputStream is = Texture.class.getResourceAsStream(path)) {

            if (is == null)
                throw new RuntimeException("Texture not found: " + path);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int read;

            while ((read = is.read(buf)) != -1) {
                baos.write(buf, 0, read);
            }

            byte[] bytes = baos.toByteArray();
            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read texture: " + path, e);
        }
    }

    public static Texture_t LoadTexture(String path) {

        int width, height, channels;
        ByteBuffer image;

        ByteBuffer fileBuffer = readResourceToBuffer(path);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(true);
            image = stbi_load_from_memory(fileBuffer, w, h, c, 4);

            if (image == null)
                throw new RuntimeException("STB failed: " + stbi_failure_reason());

            width = w.get(0);
            height = h.get(0);
            channels = 4;
        }

        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGBA8,
            width,
            height,
            0,
            GL_RGBA,
            GL_UNSIGNED_BYTE,
            image
        );

        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(image);
        glBindTexture(GL_TEXTURE_2D, 0);

        return new Texture_t(width, height, textureId, channels, path);
    }
}

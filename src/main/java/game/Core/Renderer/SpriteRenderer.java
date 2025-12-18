package game.Core.Renderer;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import game.Data;
import game.Core.Types.Verticies;
import game.Core.Types.Verticies.Quad;

import static org.lwjgl.opengl.GL30.*;

public class SpriteRenderer {
	private static float pxToNdcX(float x, int screenWidth) {
	    return (x / screenWidth) * 2.0f - 1.0f;
	}

	private static float pxToNdcY(float y, int screenHeight) {
	    return 1.0f - (y / screenHeight) * 2.0f;
	}

	private static float pxToNdcW(float w, int screenWidth) {
	    return (w / screenWidth) * 2.0f;
	}

	private static float pxToNdcH(float h, int screenHeight) {
	    return (h / screenHeight) * 2.0f;
	}
	
    private static int vao;
    private static int vbo;
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        FloatBuffer fb = BufferUtils.createFloatBuffer(Quad.verticies.length);
        fb.put(Quad.verticies);
        fb.flip();

        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    
    public static void drawTexture(
            int shaderProgram,
            int textureId,
            float x, float y,
            float width, float height
    ) {
        glUseProgram(shaderProgram);

        float ndcX = pxToNdcX(x + width * 0.5f, Data.Config.window.width);
        float ndcY = pxToNdcY(y + height * 0.5f, Data.Config.window.height);

        float ndcW = pxToNdcW(width, Data.Config.window.width);
        float ndcH = pxToNdcH(height, Data.Config.window.height);

        glUniform2f(
            glGetUniformLocation(shaderProgram, "uPosition"),
            ndcX,
            ndcY
        );

        glUniform2f(
            glGetUniformLocation(shaderProgram, "uSize"),
            ndcW,
            ndcH
        );

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(
            glGetUniformLocation(shaderProgram, "uTexture"),
            0
        );

        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
    }
}

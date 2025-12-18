package game;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.BufferUtils;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL30.*;

import game.Core.Input.Input;
import game.Core.Types.Texture_t;
import game.Core.Types.Verticies;
import game.Core.Types.WindowStruct;
import game.Core.Wrappers.Shader.ShaderHandler;
import game.Core.Wrappers.Texture.Texture;
import game.Core.Wrappers.WindowWrapper.GLFW_Window;
import game.Core.Renderer.*;

public class Main {

    public static long window;

    public static class Game {

        public static void Run() {

            GLFWErrorCallback.createPrint(System.err).set();

            if (!glfwInit())
                throw new IllegalStateException("Unable to initialize GLFW");

            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

            window = GLFW_Window.glfwCreateWindowStruct(
                new WindowStruct(1280, 720, "TEST", NULL, NULL)
            );

            if (window == NULL)
                throw new RuntimeException("Failed to create GLFW window");

            glfwSetKeyCallback(window, (w, key, scancode, action, mods) ->
                Input.ProcessKeys(w, key, action)
            );

            try (MemoryStack stack = stackPush()) {
                IntBuffer pw = stack.mallocInt(1);
                IntBuffer ph = stack.mallocInt(1);

                glfwGetWindowSize(window, pw, ph);
                GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());

                glfwSetWindowPos(
                    window,
                    (vid.width() - pw.get(0)) / 2,
                    (vid.height() - ph.get(0)) / 2
                );
            }

            glfwMakeContextCurrent(window);
            glfwSwapInterval(Data.Config.vsync);

            GL.createCapabilities();

            Data.shaderProgram = ShaderHandler.createShaderProgram(
                ShaderHandler.loadFile("/shaders/vertex.glsl"),
                ShaderHandler.loadFile("/shaders/fragment.glsl")
            );
            
            SpriteRenderer.init();

            glfwShowWindow(window);
        }

        public static long Loop() {

            glClearColor(1f, 0f, 0f, 1f);

            Texture_t texture_t = Texture.LoadTexture("/img/example.jpg");

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            
            while (!glfwWindowShouldClose(window)) {

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                SpriteRenderer.drawTexture(Data.shaderProgram, texture_t.textureId, 0, 0, 200, 200);
                
                glUseProgram(Data.shaderProgram);
                glfwSwapBuffers(window);
                glfwPollEvents();
            }

            glDeleteProgram(Data.shaderProgram);

            return 0;
        }
    }

    public static void main(String[] args) {

        Game.Run();

        if (Data.Config.debug) {
            System.out.println("App version " + Version.getVersion());
            System.out.println("windowhandle " + window);
            System.out.println("isdebug " + Data.Config.debug);
        }

        long ret = Game.Loop();

        if (Data.Config.debug) {
            System.out.println("return code : " + Long.toHexString(ret));
        }
    }
}

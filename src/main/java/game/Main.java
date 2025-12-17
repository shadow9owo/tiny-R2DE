package game;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import game.Data;
import game.Core.Types.WindowStruct;
import game.Core.Wrappers.WindowWrapper.*;
import game.Core.Input.*;

public class Main {
	public static long window;
	public static class Game
	{
		public static void Run() 
		{
			GLFWErrorCallback.createPrint(System.err).set();
			
			if (!glfwInit())
				throw new IllegalStateException("Unable to initialize GLFW");
			
			glfwDefaultWindowHints();
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); 
			
			window = GLFW_Window.glfwCreateWindowStruct(new WindowStruct(1280,720,"TEST",NULL,NULL));
			
			if (window == NULL)
				throw new RuntimeException("Failed to create the GLFW window");	
			
			glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
				Input.ProcessKeys(window,key,action);
			});
			
			try (MemoryStack stack = stackPush()) {
				IntBuffer pWidth = stack.mallocInt(1);
				IntBuffer pHeight = stack.mallocInt(1);

				glfwGetWindowSize(window, pWidth, pHeight);

				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

				glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
			}

			glfwMakeContextCurrent(window);
			glfwSwapInterval(Data.Config.vsync);

			glfwShowWindow(window);
		}
		public static long Loop()
		{
			GL.createCapabilities();

			glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			while ( !glfwWindowShouldClose(window) ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				glfwSwapBuffers(window);

				glfwPollEvents();
			}
			
			return 0;
		}
	}
	
	public static void main(String[] args) {
		Game.Run();
		if (Data.Config.debug)
		{
			System.out.println("App version" + Version.getVersion());
			System.out.println("windowhandle " + window);
			System.out.println("isdebug " + Data.Config.debug);
		}
		long ret = Game.Loop();
		if (Data.Config.debug)
		{
			System.out.println("return code : " + Long.toHexString(ret));
		}
		
		return;
	}

}

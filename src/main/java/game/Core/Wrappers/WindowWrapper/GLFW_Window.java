package game.Core.Wrappers.WindowWrapper;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;

import game.Core.Types.WindowStruct;

public class GLFW_Window {
	public static long glfwCreateWindowStruct(WindowStruct window)
	{
		return glfwCreateWindow(window.width, window.height, window.window_title, window.monitor, window.share);
	}
}

package game;

import game.Core.Types.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Data {
	public static int shaderProgram;
	public static class Config
	{
		public static boolean debug = false;
		public static WindowStruct window = new WindowStruct(1280,720,"",NULL,NULL);
		public static int vsync = 1;
	}
}

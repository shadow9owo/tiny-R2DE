package game;

import game.Core.Types.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Data {
	public static class Config
	{
		static boolean debug = false;
		static WindowStruct window = new WindowStruct(1280,720,"",NULL,NULL);
		static int vsync = 1;
	}
}

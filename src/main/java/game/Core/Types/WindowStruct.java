package game.Core.Types;

import static org.lwjgl.system.MemoryUtil.*;

public class WindowStruct {
	public int width = 300;
	public int height = 300;
	public String window_title = "";
	public long monitor = NULL;
	public long share = NULL;
	
    public WindowStruct(int width, int height,String window_title,long monitor,long share) {
        this.width = width;
        this.height = height;
        this.window_title = window_title;
        this.monitor = monitor;
        this.share = share;
    }
}

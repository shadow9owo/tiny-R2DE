package game.Core.Types;

public final class Verticies {
	public static class Quad
	{
		public static float[] verticies = {
			    -0.5f,  0.5f,    0f, 1f,
			    -0.5f, -0.5f,    0f, 0f,
			     0.5f, -0.5f,    1f, 0f,

			    -0.5f,  0.5f,    0f, 1f,
			     0.5f, -0.5f,    1f, 0f,
			     0.5f,  0.5f,    1f, 1f
		};
		
		public static int vao;
		public static int vbo;
	}
}

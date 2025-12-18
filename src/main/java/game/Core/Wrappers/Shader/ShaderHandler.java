package game.Core.Wrappers.Shader;

import static org.lwjgl.opengl.GL30.*;

public class ShaderHandler {
	public static String loadFile(String path) {
	    try (java.io.InputStream in =
	             ShaderHandler.class.getResourceAsStream(path)) {

	        if (in == null) {
	            throw new RuntimeException("Shader not found: " + path);
	        }

	        java.util.Scanner scanner = new java.util.Scanner(in, "UTF-8")
	                .useDelimiter("\\A");

	        return scanner.hasNext() ? scanner.next() : "";

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to load shader: " + path, e);
	    }
	}
	
	public static int createShaderProgram(String vertexSrc, String fragmentSrc) {

	    int vertex = compileShader(vertexSrc, GL_VERTEX_SHADER);
	    int fragment = compileShader(fragmentSrc, GL_FRAGMENT_SHADER);

	    int program = glCreateProgram();
	    glAttachShader(program, vertex);
	    glAttachShader(program, fragment);
	    glLinkProgram(program);

	    if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
	        throw new RuntimeException(
	            glGetProgramInfoLog(program)
	        );
	    }

	    glDeleteShader(vertex);
	    glDeleteShader(fragment);

	    return program;
	}
	
	private static int compileShader(String source, int type) {
	    int shader = glCreateShader(type);
	    glShaderSource(shader, source);
	    glCompileShader(shader);

	    if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
	        throw new RuntimeException(
	            glGetShaderInfoLog(shader)
	        );
	    }
	    return shader;
	}
}

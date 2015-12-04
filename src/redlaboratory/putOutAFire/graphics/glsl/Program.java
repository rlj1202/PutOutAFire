package redlaboratory.putOutAFire.graphics.glsl;

import static org.lwjgl.opengl.GL20.*;

public class Program {
	
	private int program;
	
	public Program() {
		program = glCreateProgram();
	}
	
	public int getProgramID() {
		return program;
	}
	
	public void attachShader(Shader shader) {
		glAttachShader(program, shader.getShaderID());
	}
	
	public void link() {
		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	public void use() {
		glUseProgram(program);
	}
	
	public void destroy() {
		glDeleteProgram(program);
	}
	
	public static void detachProgram() {
		glUseProgram(0);
	}
	
}

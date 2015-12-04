package redlaboratory.putOutAFire.graphics.glsl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import redlaboratory.putOutAFire.Logger;
import redlaboratory.putOutAFire.Main;

public class Shader {
	
	private int shader;
	
	public Shader(String fileName) {
		String typeStr = fileName.substring(fileName.lastIndexOf('.') + 1);
		int shaderType = -1;
		
		if (typeStr.equalsIgnoreCase("vert")) {
			shaderType = GL_VERTEX_SHADER;
		} else if (typeStr.equalsIgnoreCase("frag")) {
			shaderType = GL_FRAGMENT_SHADER;
		}
		
		if (shaderType != -1) {
			shader = glCreateShader(shaderType);
			
			StringBuilder source = new StringBuilder();
			
			try {
				InputStream in = Main.class.getResourceAsStream("/redlaboratory/putOutAFire/resources/shaders/" + fileName);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				
				String line;
				while ((line = reader.readLine()) != null) {
					source.append(line);
				}
				
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			glShaderSource(shader, source);
			glCompileShader(shader);
			
			if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
				Logger.error(fileName + " shader wasn`t able to be compiled correctly.");
			} else {
				Logger.info(source.toString());
			}
		} else {
			Logger.info(fileName + " doesn`t exist.");
		}
	}
	
	public int getShaderID() {
		return shader;
	}
	
	public void destroy() {
		glDeleteShader(shader);
	}
	
}

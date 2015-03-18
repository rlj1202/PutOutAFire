package redlaboratory.putOutAFire.graphics;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import redlaboratory.putOutAFire.Logger;
import redlaboratory.putOutAFire.utils.GraphicHelper;

public class OldFontRenderer {
	
	private static int charImageBufferID;
	
	static {
		charImageBufferID = glGenTextures();
		
		Logger.info("Font renderer create char buffer image: ID is \"" + Integer.toString(charImageBufferID) + "\"");
	}
	
	public static void renderString(String str, float x, float y, int size, int margin, int interval, Font font, Color color, boolean antialias) {
		for (int i = 0; i < str.length(); i++) {
			renderChar(str.charAt(i), x + i * interval, y, size, margin, font, color, antialias);
		}
	}
	
	public static void renderChar(char c, float x, float y, int size, int margin, Font font, Color color, boolean antialias) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		
		{
			Graphics2D gt = (Graphics2D) image.createGraphics();
			
			if (antialias) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			else gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			
			gt.setFont(font.deriveFont(font.getStyle(), size - margin * 2));
			gt.setColor(color);
			gt.drawString(Character.toString(c), margin, size - margin);
			
			gt.dispose();
		}
		
		{
			glBindTexture(GL_TEXTURE_2D, charImageBufferID);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size, size, 0, GL_RGBA, GL_UNSIGNED_BYTE, GraphicHelper.getByteBuffer(image));
		}
		
		{
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_DEPTH_TEST);
			glBindTexture(GL_TEXTURE_2D, charImageBufferID);
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1); glVertex2f(x, y);
				glTexCoord2f(0, 0); glVertex2f(x, y + size);
				glTexCoord2f(1, 0); glVertex2f(x + size, y + size);
				glTexCoord2f(1, 1); glVertex2f(x + size, y);
			}
			glEnd();
		}
	}
	
}

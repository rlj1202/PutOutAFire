package redlaboratory.putOutAFire.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import redlaboratory.putOutAFire.Logger;
import redlaboratory.putOutAFire.utils.GraphicHelper;

public class FontModule {
	
	private static HashMap<String, FontModule> fontDatas;
	
	private final String fontName;
	private final int[] textures;
	private final float[] sizeRatios;// size ratios of font chars
	
	private FontModule(String fontName, int[] textures, float[] sizeRatio) {
		this.fontName = fontName;
		this.textures = textures;
		this.sizeRatios = sizeRatio;
	}
	
	public String getFontName() {
		return fontName;
	}
	
	public int[] getTextures() {
		return textures;
	}
	
	public float getWidth(char c, float height) {
		return sizeRatios[c] * height;
	}
	
	public float getSideMargin(char c, float height) {
		return (height - getWidth(c, height)) / 2.0f;
	}
	
	public int getTextureID(char c) {
		return textures[getTextureIndex(c)];
	}
	
	public static void initialize() {
		fontDatas = new HashMap<String, FontModule>();
		
		uploadFont("Nanum", "NanumGothicBold", 30, true);
	}
	
	public static int getTextureIndex(char c) {
		return c >>> 8;
	}
	
	/**
	 * Get char location.
	 * @param c
	 * @return char location - int[] {textureIndex, x, y}
	 */
	public static int[] getCharLocation(char c) {
		int fontX = 0x00ff & c;
		int fontY = 0;
		
		while (fontX >= 16) {
			fontX -= 16;
			fontY++;
		}
		
		return new int[] {getTextureIndex(c), fontX, fontY};
	}
	
	public static void uploadFont(String storage, String fontName, int size, boolean antialias) {
		int[] textures = null;
		float[] sizeRatios = null;
		
		if (fontDatas.containsKey(storage)) {
			textures = fontDatas.get(storage).textures;
			sizeRatios = fontDatas.get(storage).sizeRatios;
		} else {
			textures = new int[0xf00];
			sizeRatios = new float[0xf0000];
		}
		
		Font font = new Font(fontName, Font.PLAIN, (int) (size * 72.0f / 96));
		
		int textureSize = size * 16;
		
		for (int i = 0; i <= 0xff; i++) {
			BufferedImage fontImage = new BufferedImage(textureSize, textureSize, BufferedImage.TYPE_INT_ARGB);
			
			{
				Graphics2D gt = (Graphics2D) fontImage.createGraphics();
				gt.setFont(font);// pixel size to point
				gt.setColor(Color.WHITE);
				
				if (antialias) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				else gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				
				FontMetrics fm = gt.getFontMetrics();
				
				for (int a = 0; a < 16; a++) {
					for (int b = 0; b < 16; b++) {
						char c = (char) ((i << 8) + a * 16 + b);
						
						float width = fm.stringWidth(Character.toString(c));
						float height = fm.getAscent() - fm.getDescent();
						
						float xMargin = (size - width) / 2.0f;
						float yMargin = (size - height) / 2.0f;
						
//						gt.drawRect(size * b, size * a, size - 1, size - 1);
//						gt.drawRect((int) (size * b + xMargin), (int) (size * a + (size - fm.getHeight()) / 2.0f), (int) width, (int) fm.getHeight());
						gt.drawString(Character.toString(c), size * b + xMargin, size * (a + 1) - yMargin);
						
						sizeRatios[c] = width / fm.getHeight();
					}
				}
				
				gt.dispose();
			}
			
			{
				int textureID = glGenTextures();
				textures[i] = textureID;
				
				glBindTexture(GL_TEXTURE_2D, textureID);
				
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
				
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, fontImage.getWidth(), fontImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, GraphicHelper.getByteBuffer(fontImage));
				
				Logger.info("Font texture uploaded: " + fontName + " " + "0x" + Integer.toHexString(i) + " to " + storage + " storage." + "\"" + textureID + "\"");
			}
			
			/*
			if (i == 0xAC || i == 0x00) {
				File file = new File("debugFontImage_" + fontName + "_" + Integer.toHexString(i) + ".png");
				file.delete();
				
				try {
					ImageIO.write(fontImage, "png", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			 */
		}
		
		fontDatas.put(storage, new FontModule(fontName, textures, sizeRatios));
	}
	
	public static FontModule getFontData(String storage) {
		return fontDatas.get(storage);
	}
	
	public static float renderString(String str, float size, float x, float y, String storage) {
		float xPos = x;
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			if (c == 0x20) xPos += size / 3.0f;
			else xPos += renderChar(c, size, xPos, y, storage);
		}
		
		return xPos - x;
	}
	
	public static float renderChar(char c, float size, float x, float y, String storage) {
		FontModule data = fontDatas.get(storage);
		
		float width = data.getWidth(c, size);
		float xMargin = data.getSideMargin(c, size);
		
		int[] charLoc = getCharLocation(c);
		
		int fontX = charLoc[1];
		int fontY = charLoc[2];
		
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glBindTexture(GL_TEXTURE_2D, data.getTextureID(c));
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(fontX       / 16.0f, (fontY + 1) / 16.0f); glVertex2f(x        - xMargin, y       );
			glTexCoord2f(fontX       / 16.0f, fontY       / 16.0f); glVertex2f(x        - xMargin, y + size);
			glTexCoord2f((fontX + 1) / 16.0f, fontY       / 16.0f); glVertex2f(x + size - xMargin, y + size);
			glTexCoord2f((fontX + 1) / 16.0f, (fontY + 1) / 16.0f); glVertex2f(x + size - xMargin, y       );
		}
		glEnd();
		
		return width;
	}
	
}

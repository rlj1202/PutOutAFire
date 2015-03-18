package redlaboratory.putOutAFire.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import redlaboratory.putOutAFire.Logger;
import redlaboratory.putOutAFire.utils.GraphicHelper;

public class Texture {
	
	public static Texture FIRE;
	public static Texture FIRE_EXTINGUISHER;
	
	public static Texture HEART;
	
	public static Texture STONE_TILE_1;
	
	public static Texture MEDIC_KIT;
	
	public static Texture FONT;
	
	//
	
	public static Texture CHAR_TEX;
	public static Texture LOGO;
	public static Texture TEST_TEX_1;
	public static Texture TEST_TEX_2;
	public static Texture TILE_TEX_1;
	public static Texture TILE_TEX_2;
	public static Texture AUTOMATIC_DOOR;
	
	public static Texture testtesttesttesttesttest;
	
	private static ArrayList<Texture> animatedTextures;
	
	static {
		animatedTextures = new ArrayList<Texture>();
		
		int fire_0 = uploadTexture("fire_0.png");
		int fire_1 = uploadTexture("fire_1.png");
		int fire_2 = uploadTexture("fire_2.png");
		int fire_3 = uploadTexture("fire_3.png");
		int fire_4 = uploadTexture("fire_4.png");
		int fire_5 = uploadTexture("fire_5.png");
		
		int fireExtinguisher = uploadTexture("fireExtinguisher.png");
		
		int heart = uploadTexture("heart.png");
		
		int stoneTile = uploadTexture("stoneTile_1.png");
		
		int medicKit = uploadTexture("medicKit.png");
		
		int font = uploadTexture("font.png");
		
		//
		
		int c = uploadTexture("Character.png");
		int logo = uploadTexture("PutOutAFireLogo.png");
		int t1 = uploadTexture("Test.png");
		int t2 = uploadTexture("Test2.png");
		int tile1 = uploadTexture("TestTile1.png");
		int tile2 = uploadTexture("TestTile2.png");
		int automaticDoor = uploadTexture("automaticDoor.png");
		int testtesttesttesttest = uploadTexture("healthbar_bg.png");
		
		FIRE = new Texture(fire_0, fire_0, fire_0, fire_0, fire_0, fire_0,
				fire_1, fire_1, fire_1, fire_1, fire_1, fire_1,
				fire_2, fire_2, fire_2, fire_2, fire_2, fire_2,
				fire_3, fire_3, fire_3, fire_3, fire_3, fire_3,
				fire_4, fire_4, fire_4, fire_4, fire_4, fire_4,
				fire_5, fire_5);
		FIRE_EXTINGUISHER = new Texture(fireExtinguisher);
		HEART = new Texture(heart);
		STONE_TILE_1 = new Texture(stoneTile);
		MEDIC_KIT = new Texture(medicKit);
		FONT = new Texture(font);
		
		//
		
		CHAR_TEX = new Texture(c);
		LOGO = new Texture(logo);
		TEST_TEX_1 = new Texture(t1);
		TEST_TEX_2 = new Texture(t2);
		TILE_TEX_1 = new Texture(tile1);
		TILE_TEX_2 = new Texture(tile2);
		AUTOMATIC_DOOR = new Texture(automaticDoor);
		testtesttesttesttesttest = new Texture(testtesttesttesttest);
	}
	
	private int[] textureIDs;
	private int curFrame;
	
	public Texture(String... names) {
		textureIDs = new int[names.length];
		
		int i = 0;
		for (String name : names) {
			textureIDs[i] = uploadTexture(name);
			i++;
		}
		
		curFrame = 0;
		if (names.length > 1) Texture.animatedTextures.add(this);
	}
	
	public Texture(int... textureIDs) {
		this.textureIDs = textureIDs;
		
		curFrame = 0;
		if (textureIDs.length > 1) Texture.animatedTextures.add(this);
	}
	
	public Texture(Texture... textures) {
		int frames = 0;
		for (Texture texture : textures) {
			frames += texture.textureIDs.length;
		}
		textureIDs = new int[frames];
		
		int frame = 0;
		for (Texture texture : textures) {
			for (int i = 0; i < texture.textureIDs.length; i++) {
				textureIDs[frame] = texture.textureIDs[i];
				frame++;
			}
		}
		
		curFrame = 0;
		if (frames > 1) Texture.animatedTextures.add(this);
	}
	
	public int getTextureID() {
		return textureIDs[curFrame];
	}
	
	public int[] getTextureIDs() {
		return textureIDs;
	}
	
	public static void tick() {
		for (Texture texture : animatedTextures) {
			if (texture.curFrame == texture.textureIDs.length - 1) texture.curFrame = 0;
			else texture.curFrame++;
		}
	}
	
	public static int uploadTexture(String name) {
		int textureID = uploadTexture(GraphicHelper.getBufferedImage("/redlaboratory/putOutAFire/resources/images/" + name));
		
		Logger.info("Texture uploaded: \"" + name + "\" to \"" + textureID + "\"");
		
		return textureID;
	}
	
	public static int uploadTexture(BufferedImage image) {
		int textureID = glGenTextures();
		{
			glBindTexture(GL_TEXTURE_2D, textureID);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, GraphicHelper.getByteBuffer(image));
		}
		
		return textureID;
	}
	
}

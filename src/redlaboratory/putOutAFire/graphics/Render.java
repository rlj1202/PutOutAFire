package redlaboratory.putOutAFire.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import redlaboratory.putOutAFire.Block;
import redlaboratory.putOutAFire.Core;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.entity.LivingEntity;
import redlaboratory.putOutAFire.entity.Particle;
import redlaboratory.putOutAFire.entity.Player;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.item.FireExtinguisher;
import redlaboratory.putOutAFire.item.Item;

public class Render {
	
	public static void initialize() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -10, 10);
		
		glMatrixMode(GL_MODELVIEW);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		glClearColor(0, 0, 0, 0);
	}
	
	private void drawMap(Game game, Core core) {
		int shadowZ = game.getMap().getHeight();
		int shadowR = (Display.getWidth() > Display.getHeight()) ? (Display.getWidth()) : (Display.getHeight());
		
		for (int z = 0; z < game.getMap().getHeight(); z++) {
			int yStart = game.getCamera().Y >= 0 ? (int) (game.getCamera().Y / game.getCamera().ZOOM) : 0;
			int yEnd = game.getCamera().Y + Display.getHeight() >= 0 ? (int) ((game.getCamera().Y + Display.getHeight()) / game.getCamera().ZOOM) : 0;
			
			for (int y = yStart; y <= yEnd; y++) {
				int xStart = game.getCamera().X >= 0 ? (int) (game.getCamera().X / game.getCamera().ZOOM) : 0;
				int xEnd = game.getCamera().X + Display.getWidth() >= 0 ? (int) ((game.getCamera().X + Display.getWidth()) / game.getCamera().ZOOM) : 0;
				
				for (int x = xStart; x <= xEnd; x++) {
					// Render: blocks
					Block block = game.getMap().getBlock(x, y, z);
					
					if (block == null) continue;
					
					int dX = game.getCamera().ZOOM * x;// Display
					int dY = game.getCamera().ZOOM * y;
					
					drawQuad(dX, dY, z, game.getCamera().ZOOM, game.getCamera().ZOOM, block.getTexture());
					
					// Render: shadow
					if (game.getViewEntity() != null) {
						if (!block.isThroughable()) {
							glDisable(GL_TEXTURE_2D);
							glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
							glBegin(GL_QUADS);
							{
								float pcx = game.getCamera().ZOOM * game.getViewEntity().getCenterX();// Player display center x
								float pcy = game.getCamera().ZOOM * game.getViewEntity().getCenterY();// Player display center y
								
								// Left
								double angle1 = Math.atan2(y + 1 - game.getViewEntity().getCenterY(), x     - game.getViewEntity().getCenterX());
								double angle2 = Math.atan2(y     - game.getViewEntity().getCenterY(), x     - game.getViewEntity().getCenterX());
								glVertex3f(dX, dY, shadowZ);
								glVertex3f(dX, dY + game.getCamera().ZOOM, shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle1) * shadowR), pcy + (float) (Math.sin(angle1) * shadowR), shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle2) * shadowR), pcy + (float) (Math.sin(angle2) * shadowR), shadowZ);
								
								// Right
								double angle3 = Math.atan2(y + 1 - game.getViewEntity().getCenterY(), x + 1 - game.getViewEntity().getCenterX());
								double angle4 = Math.atan2(y     - game.getViewEntity().getCenterY(), x + 1 - game.getViewEntity().getCenterX());
								glVertex3f(dX + game.getCamera().ZOOM, dY, shadowZ);
								glVertex3f(dX + game.getCamera().ZOOM, dY + game.getCamera().ZOOM, shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle3) * shadowR), pcy + (float) (Math.sin(angle3) * shadowR), shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle4) * shadowR), pcy + (float) (Math.sin(angle4) * shadowR), shadowZ);
								
								// Up
								double angle5 = Math.atan2(y + 1 - game.getViewEntity().getCenterY(), x     - game.getViewEntity().getCenterX());
								double angle6 = Math.atan2(y + 1 - game.getViewEntity().getCenterY(), x + 1 - game.getViewEntity().getCenterX());
								glVertex3f(dX, dY + game.getCamera().ZOOM, shadowZ);
								glVertex3f(dX + game.getCamera().ZOOM, dY + game.getCamera().ZOOM, shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle6) * shadowR), pcy + (float) (Math.sin(angle6) * shadowR), shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle5) * shadowR), pcy + (float) (Math.sin(angle5) * shadowR), shadowZ);
								
								// Down
								double angle7 = Math.atan2(y     - game.getViewEntity().getCenterY(), x     - game.getViewEntity().getCenterX());
								double angle8 = Math.atan2(y     - game.getViewEntity().getCenterY(), x + 1 - game.getViewEntity().getCenterX());
								glVertex3f(dX, dY, shadowZ);
								glVertex3f(dX + game.getCamera().ZOOM, dY, shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle8) * shadowR), pcy + (float) (Math.sin(angle8) * shadowR), shadowZ);
								glVertex3f(pcx + (float) (Math.cos(angle7) * shadowR), pcy + (float) (Math.sin(angle7) * shadowR), shadowZ);
							}
							glEnd();
						}
					}
				}
			}
		}
	}
	
	private void drawEntities(Game game, Core core) {
		for (Entity entity : game.getMap().getEntities()) {
			if (entity instanceof Particle) {
				Particle particle = (Particle) entity;
				drawQuad(game.getCamera().ZOOM * entity.getX(), game.getCamera().ZOOM * entity.getY(), entity.getZ(), game.getCamera().ZOOM * entity.getWidth(), game.getCamera().ZOOM * entity.getHeight(), particle.getColor());
				
				continue;
			}
			
			drawQuad(game.getCamera().ZOOM * entity.getX(), game.getCamera().ZOOM * entity.getY(), entity.getZ(), game.getCamera().ZOOM * entity.getWidth(), game.getCamera().ZOOM * entity.getHeight(), entity.getTexture());
			
			if (entity instanceof Player) {
				Player player = (Player) entity;
				Item item = player.getItemOnHand();
				
				if (item != null) {
					float x = player.getX() + player.getWidth() + item.getX();
					float y = player.getY() + item.getY();
					float z = player.getZ() + item.getZ();
					
					drawQuad(game.getCamera().ZOOM * x, game.getCamera().ZOOM * y, z, game.getCamera().ZOOM * item.getWidth(), game.getCamera().ZOOM * item.getHeight(), player.getItemOnHand().getTexture());
				}
			}
		}
	}
	
	private void drawHUDHealth(Game game, Core core) {
		if (game.getViewEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) game.getViewEntity();
			
			glPushMatrix();
			{
				glDisable(GL_DEPTH_TEST);
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, Texture.HEART.getTextureID());
				glBegin(GL_QUADS);
				{
					float size = 80.0f;
					int xOffset = 10;
					int yOffset = 10;
					
					glTexCoord2f(0.5f, 1); glVertex2f(Display.getWidth() - size - xOffset, yOffset);
					glTexCoord2f(0.5f, 0); glVertex2f(Display.getWidth() - size - xOffset, size + yOffset);
					glTexCoord2f(1, 0); glVertex2f(Display.getWidth() - xOffset, size + yOffset);
					glTexCoord2f(1, 1); glVertex2f(Display.getWidth() - xOffset, yOffset);
					
					glTexCoord2f(0, 1); glVertex2f(Display.getWidth() - size - xOffset, yOffset);
					glTexCoord2f(0, 1 - entity.getHealth() / entity.getMaxHealth()); glVertex2f(Display.getWidth() - size - xOffset, yOffset + entity.getHealth() * size / entity.getMaxHealth());
					glTexCoord2f(0.5f, 1 - entity.getHealth() / entity.getMaxHealth()); glVertex2f(Display.getWidth() - xOffset, yOffset + entity.getHealth() * size / entity.getMaxHealth());
					glTexCoord2f(0.5f, 1); glVertex2f(Display.getWidth() - xOffset, yOffset);
				}
				glEnd();
				
				String text = "Health " + entity.getHealth() + " / " + entity.getMaxHealth();
				float size = 16;
				drawText(Display.getWidth() - 90 - size, size, size, Color.WHITE, text, TextRenderAttribute.RIGHT);
			}
			glPopMatrix();
		}
	}
	
	private void drawHUDItems(Game game, Core core) {
		if (game.getViewEntity() instanceof Player) {
			Player player = (Player) game.getViewEntity();
			
			if (player.getItemOnHand() != null) {
				Item item = player.getItemOnHand();
				
				glPushMatrix();
				{
					glDisable(GL_DEPTH_TEST);
					glEnable(GL_TEXTURE_2D);
					glBindTexture(GL_TEXTURE_2D, item.getTexture().getTextureID());
					glBegin(GL_QUADS);
					{
						float size = 60;
						
						glTexCoord2f(0, 1); glVertex2f(10, 10);
						glTexCoord2f(0, 0); glVertex2f(10, 10 + item.getHeight() * size / item.getWidth());
						glTexCoord2f(1, 0); glVertex2f(10 + size, 10 + item.getHeight() * size / item.getWidth());
						glTexCoord2f(1, 1); glVertex2f(10 + size, 10);
					}
					glEnd();
					
					float size = 16;
					drawText(90 + size, size, size, Color.WHITE, item.getTitle(), TextRenderAttribute.LEFT);
					
					if (item instanceof FireExtinguisher) {
						FireExtinguisher f = (FireExtinguisher) item;
						
						size = 16;
						drawText(90 + size, size * 3, size, Color.WHITE, "Capacity " + f.getCapacity(), TextRenderAttribute.LEFT);
					}
				}
				glPopMatrix();
			}
		}
	}
	
	public void drawGame(Game game, Core core) {
		if (game == null) return;
		if (game.getMap() == null) return;
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.1f); 
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
		
		glPushMatrix();
		{
			for (Renderer renderer : game.getRenderers()) {
				renderer.pre(game, core, this);
			}
			
			glTranslatef(-game.getCamera().X, -game.getCamera().Y, 0);
			
			drawMap(game, core);
			drawEntities(game, core);
		}
		glPopMatrix();
		
		drawHUDHealth(game, core);
		drawHUDItems(game, core);
		
		for (Renderer renderer : game.getRenderers()) {
			renderer.post(game, core, this);
		}
	}
	
	public void drawText(float x, float y, float size, Color color, String text, TextRenderAttribute attribute) {
		if (attribute == null) attribute = TextRenderAttribute.LEFT;
		switch (attribute) {
		case LEFT:
			break;
		case CENTER:
			x -= text.length() * size / 2;
			
			break;
		case RIGHT:
			x -= text.length() * size;
			
			break;
		}
		
		glEnable(GL_TEXTURE_2D);
		glColor4f(color.R, color.G, color.B, color.ALPHA);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		glBindTexture(GL_TEXTURE_2D, Texture.FONT.getTextureID());
		
		glPushMatrix();
		{
			float width = (float) 26;
			float height = (float) 4;
			
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				
				glBegin(GL_QUADS);
				{
					switch (c) {
					case 'a':
						glTexCoord2f(0 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(0 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(1 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(1 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'b':
						glTexCoord2f(1 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(1 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(2 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(2 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'c':
						glTexCoord2f(2 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(2 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(3 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(3 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'd':
						glTexCoord2f(3 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(3 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(4 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(4 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'e':
						glTexCoord2f(4 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(4 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(5 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(5 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'f':
						glTexCoord2f(5 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(5 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(6 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(6 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'g':
						glTexCoord2f(6 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(6 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(7 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(7 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'h':
						glTexCoord2f(7 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(7 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(8 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(8 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'i':
						glTexCoord2f(8 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(8 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(9 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(9 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'j':
						glTexCoord2f(9 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(9 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(10 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(10 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'k':
						glTexCoord2f(10 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(10 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(11 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(11 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'l':
						glTexCoord2f(11 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(11 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(12 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(12 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'm':
						glTexCoord2f(12 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(12 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(13 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(13 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'n':
						glTexCoord2f(13 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(13 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(14 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(14 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'o':
						glTexCoord2f(14 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(14 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(15 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(15 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'p':
						glTexCoord2f(15 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(15 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(16 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(16 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'q':
						glTexCoord2f(16 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(16 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(17 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(17 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'r':
						glTexCoord2f(17 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(17 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(18 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(18 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 's':
						glTexCoord2f(18 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(18 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(19 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(19 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 't':
						glTexCoord2f(19 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(19 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(20 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(20 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'u':
						glTexCoord2f(20 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(20 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(21 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(21 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'v':
						glTexCoord2f(21 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(21 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(22 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(22 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'w':
						glTexCoord2f(22 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(22 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(23 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(23 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'x':
						glTexCoord2f(23 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(23 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(24 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(24 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'y':
						glTexCoord2f(24 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(24 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(25 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(25 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'z':
						glTexCoord2f(25 / width, 2 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(25 / width, 1 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(26 / width, 1 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(26 / width, 2 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'A':
						glTexCoord2f(0 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(0 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(1 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(1 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'B':
						glTexCoord2f(1 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(1 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(2 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(2 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'C':
						glTexCoord2f(2 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(2 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(3 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(3 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'D':
						glTexCoord2f(3 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(3 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(4 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(4 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'E':
						glTexCoord2f(4 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(4 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(5 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(5 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'F':
						glTexCoord2f(5 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(5 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(6 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(6 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'G':
						glTexCoord2f(6 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(6 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(7 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(7 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'H':
						glTexCoord2f(7 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(7 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(8 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(8 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'I':
						glTexCoord2f(8 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(8 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(9 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(9 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'J':
						glTexCoord2f(9 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(9 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(10 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(10 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'K':
						glTexCoord2f(10 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(10 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(11 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(11 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'L':
						glTexCoord2f(11 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(11 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(12 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(12 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'M':
						glTexCoord2f(12 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(12 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(13 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(13 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'N':
						glTexCoord2f(13 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(13 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(14 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(14 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'O':
						glTexCoord2f(14 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(14 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(15 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(15 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'P':
						glTexCoord2f(15 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(15 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(16 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(16 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'Q':
						glTexCoord2f(16 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(16 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(17 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(17 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'R':
						glTexCoord2f(17 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(17 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(18 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(18 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'S':
						glTexCoord2f(18 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(18 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(19 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(19 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'T':
						glTexCoord2f(19 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(19 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(20 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(20 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'U':
						glTexCoord2f(20 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(20 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(21 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(21 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'V':
						glTexCoord2f(21 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(21 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(22 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(22 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'W':
						glTexCoord2f(22 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(22 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(23 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(23 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'X':
						glTexCoord2f(23 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(23 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(24 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(24 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'Y':
						glTexCoord2f(24 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(24 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(25 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(25 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case 'Z':
						glTexCoord2f(25 / width, 1 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(25 / width, 0 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(26 / width, 0 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(26 / width, 1 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '.':
						glTexCoord2f(0 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(0 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(1 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(1 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case ',':
						glTexCoord2f(1 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(1 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(2 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(2 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '?':
						glTexCoord2f(2 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(2 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(3 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(3 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '!':
						glTexCoord2f(3 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(3 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(4 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(4 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '+':
						glTexCoord2f(4 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(4 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(5 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(5 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '-':
						glTexCoord2f(5 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(5 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(6 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(6 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '*':
						glTexCoord2f(6 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(6 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(7 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(7 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '/':
						glTexCoord2f(7 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(7 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(8 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(8 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '\'':
						glTexCoord2f(8 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(8 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(9 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(9 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '\"':
						glTexCoord2f(9 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(9 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(10 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(10 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '(':
						glTexCoord2f(10 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(10 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(11 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(11 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case ')':
						glTexCoord2f(11 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(11 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(12 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(12 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case ':':
						glTexCoord2f(12 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(12 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(13 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(13 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case ';':
						glTexCoord2f(13 / width, 3 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(13 / width, 2 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(14 / width, 2 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(14 / width, 3 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '0':
						glTexCoord2f(0 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(0 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(1 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(1 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '1':
						glTexCoord2f(1 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(1 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(2 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(2 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '2':
						glTexCoord2f(2 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(2 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(3 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(3 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '3':
						glTexCoord2f(3 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(3 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(4 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(4 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '4':
						glTexCoord2f(4 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(4 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(5 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(5 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '5':
						glTexCoord2f(5 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(5 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(6 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(6 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '6':
						glTexCoord2f(6 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(6 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(7 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(7 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '7':
						glTexCoord2f(7 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(7 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(8 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(8 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '8':
						glTexCoord2f(8 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(8 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(9 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(9 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					case '9':
						glTexCoord2f(9 / width, 4 / height); glVertex2f(x + size * i,        y);
						glTexCoord2f(9 / width, 3 / height); glVertex2f(x + size * i,        y + size);
						glTexCoord2f(10 / width, 3 / height); glVertex2f(x + size * i + size, y + size);
						glTexCoord2f(10 / width, 4 / height); glVertex2f(x + size * i + size, y);
						
						break;
					}
				}
				glEnd();
			}
		}
		glPopMatrix();
	}
	
	public void drawQuad(float x, float y, float z, float width, float height, Texture texture) {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 1); glVertex3f(x, y, z);
			glTexCoord2f(0, 0); glVertex3f(x, y + height, z);
			glTexCoord2f(1, 0); glVertex3f(x + width, y + height, z);
			glTexCoord2f(1, 1); glVertex3f(x + width, y, z);
		}
		glEnd();
	}
	
	public void drawQuad(float x, float y, float z, float width, float height, Color color) {
		glDisable(GL_TEXTURE_2D);
		glColor4f(color.R, color.G, color.B, color.ALPHA);
		glBegin(GL_QUADS);
		{
			glVertex3f(x, y, z);
			glVertex3f(x, y + height, z);
			glVertex3f(x + width, y + height, z);
			glVertex3f(x + width, y, z);
		}
		glEnd();
	}
	
	public void drawQuad(float x, float y, float width, float height, Color color) {
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glColor4f(color.R, color.G, color.B, color.ALPHA);
		glBegin(GL_QUADS);
		{
			glVertex2f(x, y);
			glVertex2f(x, y + height);
			glVertex2f(x + width, y + height);
			glVertex2f(x + width, y);
		}
		glEnd();
		glEnable(GL_DEPTH_TEST);
	}
	
	public void effectColor(Color color, int length) {
		glPushMatrix();
		{
			glDisable(GL_DEPTH_TEST);
			glDisable(GL_TEXTURE_2D);
			glColor4f(color.R, color.G, color.B, length);
			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(Display.getWidth(), 0);
				glVertex2f(Display.getWidth(), Display.getHeight());
				glVertex2f(0, Display.getHeight());
			}
			glEnd();
		}
		glPopMatrix();
	}
	
}

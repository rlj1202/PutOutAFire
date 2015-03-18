package redlaboratory.putOutAFire;

import java.util.LinkedList;

import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.game.Game;

public class Map {
	
	private final int width;
	private final int length;
	private final int height;
	
	private Block[] blocks;
	private LinkedList<Entity> entities;
	
	private Game curGame;
	
	public Map(int width, int length, int height, Game curGame) {
		this.width = width;
		this.length = length;
		this.height = height;
		
		blocks = new Block[width * length * height];
		entities = new LinkedList<Entity>();
		
		this.curGame = curGame;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Game getCurrentGame() {
		return curGame;
	}
	
	public void setCurrentGame(Game curGame) {
		this.curGame = curGame;
	}
	
	public void setBlock(int x, int y, int z, Block block) {
		if (x < 0 || y < 0 || z < 0) {
			return;
		}
		
		if (x >= width || y >= length || z >= height) {
			return;
		}
		
		blocks[(x + y * width) + width * length * z] = block;
	}
	
	public Block getBlock(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0) {
			return null;
		}
		
		if (x >= width || y >= length || z >= height) {
			return null;
		}
		
		return blocks[(x + y * width) + width * length * z];
	}
	
	public Block[] getBlocks() {
		return blocks;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public LinkedList<Entity> getEntities() {
		return entities;
	}
	
	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.tick();
		}
	}
	
}

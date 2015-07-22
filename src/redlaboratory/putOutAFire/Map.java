package redlaboratory.putOutAFire;

import java.util.ArrayList;
import java.util.LinkedList;

import redlaboratory.putOutAFire.entity.Entity;

public class Map {
	
	final int width;
	final int length;
	final int height;
	
	private Block[] blocks;
	LinkedList<Entity> entities;
	
	ArrayList<Entity> addEntitiesQueue;
	ArrayList<Entity> removeEntitiesQueue;
	
	public Map(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		
		blocks = new Block[width * length * height];
		entities = new LinkedList<Entity>();
		
		addEntitiesQueue = new ArrayList<Entity>();
		removeEntitiesQueue = new ArrayList<Entity>();
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
		addEntitiesQueue.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		removeEntitiesQueue.add(entity);
	}
	
	public LinkedList<Entity> getEntities() {
		return entities;
	}
	
}

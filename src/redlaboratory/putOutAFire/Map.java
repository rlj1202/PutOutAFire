package redlaboratory.putOutAFire;

import java.util.ArrayList;
import java.util.LinkedList;

import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.game.Game;

public class Map {
	
	private final int width;
	private final int length;
	private final int height;
	
	private Block[] blocks;
	private LinkedList<Entity> entities;
	
	private ArrayList<Entity> addEntitiesQueue;
	private ArrayList<Entity> removeEntitiesQueue;
	
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
	
	public void tick(Game game) {
		for (Entity entity : addEntitiesQueue) {
			entities.add(entity);
		}
		
		for (Entity entity : removeEntitiesQueue) {
			entities.remove(entity);
		}
		
		addEntitiesQueue.clear();
		removeEntitiesQueue.clear();
		
		for (Entity entity : entities) {
			entity.setX(entity.getX() + entity.getXS());
			entity.setY(entity.getY() + entity.getYS());
			entity.setZ(entity.getZ() + entity.getZS());
			
			Block block = getBlock((int) (entity.getX() + entity.getWidth() / 2), (int) (entity.getY() + entity.getHeight() / 2), (int) entity.getZ());
			
			if (block != null) {
				entity.setXS(entity.getXS() * block.getFrictionalForce());
				entity.setYS(entity.getYS() * block.getFrictionalForce());
				entity.setZS(entity.getZS() * block.getFrictionalForce());
			}
			
			if (entity.isPhysicsable()) {
				collisionTestWithMap(entity);
				collisionTestWithBlock(entity);
				collisionTestWithEntity(entity);
			}
			
			interactingWithEntity(entity);
		}
		
		for (Entity entity : entities) {
			entity.tick(game, this);
		}
	}
	
	private void collisionTestWithMap(Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		
		float xS = entity.getXS();
		float yS = entity.getYS();
		
		float width = entity.getWidth();
		float height = entity.getHeight();
		
		if (x < 0) {
			x = 0;
			xS = 0;
		}
		
		if (y < 0) {
			y = 0;
			yS = 0;
		}
		
		if (x + width > this.width) {
			x = this.width - width;
			xS = 0;
		}
		
		if (y + height > this.length) {
			y = this.length - height;
			yS = 0;
		}
		
		entity.setX(x);
		entity.setY(y);
		entity.setXS(xS);
		entity.setYS(yS);
	}
	
	private void collisionTestWithBlock(Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		float z = entity.getZ();
		
		float xS = entity.getXS();
		float yS = entity.getYS();
		
		float width = entity.getWidth();
		float height = entity.getHeight();
		
		SquareArea playerA = new SquareArea(x, y, width, height);
		
		for (int bY = (int) y; bY <= (int) (y + height); bY++) {
			for (int bX = (int) x; bX <= (int) (x + width); bX++) {
				Block block = getBlock(bX, bY, (int) z + 1);
				
				if (block != null) {
					if (!block.isThroughable()) {
						SquareArea blockA = new SquareArea(bX, bY, 1, 1);
						
						SquareArea inter = playerA.getIntersection(blockA);
						
						if (inter != null) {// Collision detected
							if (inter.getWidth() > inter.getHeight()) {
								if (playerA.getCenterY() < blockA.getCenterY()) {
									y = y - inter.getHeight();
								} else {
									y = y + inter.getHeight();
								}
								
								yS = 0;
							} else if (inter.getHeight() > inter.getWidth()) {
								if (playerA.getCenterX() < blockA.getCenterX()) {
									x = x - inter.getWidth();
								} else {
									x = x + inter.getWidth();
								}
								
								xS = 0;
							} else {
								if (playerA.getCenterY() < blockA.getCenterY()) {
									y = y - inter.getHeight();
								} else {
									y = y + inter.getHeight();
								}
								
								if (playerA.getCenterX() < blockA.getCenterX()) {
									x = x - inter.getWidth();
								} else {
									x = x + inter.getWidth();
								}
								
								xS = 0;
								yS = 0;
							}
						}
					}
				}
			}
		}
		
		entity.setX(x);
		entity.setY(y);
		entity.setXS(xS);
		entity.setYS(yS);
	}
	
	private void collisionTestWithEntity(Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		
		float xS = entity.getXS();
		float yS = entity.getYS();
		
		for (Entity e : entities) {
			if (e.equals(this)) continue;
			if (e.isThroughable()) continue;
			
			SquareArea playerA = entity.getArea();
			SquareArea entityA = e.getArea();
			
			SquareArea inter = playerA.getIntersection(entityA);// Intersection
			
			if (inter != null) {// Collision detected
				if (inter.getWidth() > inter.getHeight()) {
					if (playerA.getCenterY() < entityA.getCenterY()) {
						y = y - inter.getHeight();
					} else {
						y = y + inter.getHeight();
					}
					
					yS = 0;
				} else if (inter.getHeight() > inter.getWidth()) {
					if (playerA.getCenterX() < entityA.getCenterX()) {
						x = x - inter.getWidth();
					} else {
						x = x + inter.getWidth();
					}
					
					xS = 0;
				} else {
					if (playerA.getCenterY() < entityA.getCenterY()) {
						y = y - inter.getHeight();
					} else {
						y = y + inter.getHeight();
					}
					
					if (playerA.getCenterX() < entityA.getCenterX()) {
						x = x - inter.getWidth();
					} else {
						x = x + inter.getWidth();
					}
					
					xS = 0;
					yS = 0;
				}
			}
		}
		
		entity.setX(x);
		entity.setY(y);
		entity.setXS(xS);
		entity.setYS(yS);
	}
	
	@SuppressWarnings("unused")
	private void collisionTestWithBlockSimple(Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		float z = entity.getZ();
		
		float xS = entity.getXS();
		float yS = entity.getYS();
		
		Block block = getBlock((int) entity.getCenterX(), (int) entity.getCenterY(), (int) z + 1);
		
		if (block != null) {
			if (!block.isThroughable()) {
				SquareArea particleA = entity.getArea();
				SquareArea blockA = new SquareArea((int) entity.getCenterX(), (int) entity.getCenterY(), 1, 1);
				
				SquareArea inter = particleA.getIntersection(blockA);
				
				if (inter != null) {// Collision detected
					if (inter.getWidth() > inter.getHeight()) {
						if (particleA.getCenterY() < blockA.getCenterY()) {
							y = y - inter.getHeight();
						} else {
							y = y + inter.getHeight();
						}
						
						yS = 0;
					} else if (inter.getHeight() > inter.getWidth()) {
						if (particleA.getCenterX() < blockA.getCenterX()) {
							x = x - inter.getWidth();
						} else {
							x = x + inter.getWidth();
						}
						
						xS = 0;
					} else {
						if (particleA.getCenterY() < blockA.getCenterY()) {
							y = y - inter.getHeight();
						} else {
							y = y + inter.getHeight();
						}
						
						if (particleA.getCenterX() < blockA.getCenterX()) {
							x = x - inter.getWidth();
						} else {
							x = x + inter.getWidth();
						}
						
						xS = 0;
						yS = 0;
					}
				}
			}
		}
		
		entity.setX(x);
		entity.setY(y);
		entity.setXS(xS);
		entity.setYS(yS);
	}
	
	private void interactingWithEntity(Entity entity) {
		entity.getInteractingEntities().clear();
		
		SquareArea area = entity.getArea();
		
		for (Entity e : entities) {
			if (area.isCollided(e.getArea())) {
				entity.getInteractingEntities().add(e);
			}
		}
	}
	
}

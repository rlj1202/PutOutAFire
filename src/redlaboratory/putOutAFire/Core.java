package redlaboratory.putOutAFire;

import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Render;

public class Core {
	
	public int FPS_1 = 0;
	public float FPS_2 = 0.0f;
	
	private Game curGame;
	private Render render;
	
	public Core() {
		render = new Render();
	}
	
	public Game getCurrentGame() {
		return curGame;
	}
	
	public float[] getListenerPosition() {
		return curGame.getListenerPosition();
	}
	
	public void setCurrentGame(Game curGame, boolean initialize) {
		this.curGame = curGame;
		if (initialize) curGame.initialize();
	}
	
	public void tick() {
		if (curGame != null) {
			Map map = curGame.getMap();
			
			if (map == null) return;
			
			for (Entity entity : map.addEntitiesQueue) {
				map.entities.add(entity);
			}
			
			for (Entity entity : map.removeEntitiesQueue) {
				map.entities.remove(entity);
			}
			
			map.addEntitiesQueue.clear();
			map.removeEntitiesQueue.clear();
			
			for (Entity entity : map.entities) {
				entity.setX(entity.getX() + entity.getXS());
				entity.setY(entity.getY() + entity.getYS());
				entity.setZ(entity.getZ() + entity.getZS());
				
				Block block = map.getBlock((int) (entity.getX() + entity.getWidth() / 2), (int) (entity.getY() + entity.getHeight() / 2), (int) entity.getZ());
				
				if (block != null) {
					entity.setXS(entity.getXS() * block.getFrictionalForce());
					entity.setYS(entity.getYS() * block.getFrictionalForce());
					entity.setZS(entity.getZS() * block.getFrictionalForce());
				}
				
				if (entity.isPhysicsable()) {
					collisionTestWithMap(map, entity);
					collisionTestWithBlock(map, entity);
					collisionTestWithEntity(map, entity);
				}
				
				interactingWithEntity(map, entity);
			}
			
			for (Entity entity : map.entities) {
				entity.tick(curGame, map);
			}
			
			curGame.tick(this);
			
			render.drawGame(curGame, this);
		}
	}
	
	private void collisionTestWithMap(Map map, Entity entity) {
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
		
		if (x + width > map.width) {
			x = map.width - width;
			xS = 0;
		}
		
		if (y + height > map.length) {
			y = map.length - height;
			yS = 0;
		}
		
		entity.setX(x);
		entity.setY(y);
		entity.setXS(xS);
		entity.setYS(yS);
	}
	
	private void collisionTestWithBlock(Map map, Entity entity) {
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
				Block block = map.getBlock(bX, bY, (int) z + 1);
				
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
	
	private void collisionTestWithEntity(Map map, Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		
		float xS = entity.getXS();
		float yS = entity.getYS();
		
		for (Entity e : map.entities) {
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
	private void collisionTestWithBlockSimple(Map map, Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		float z = entity.getZ();
		
		float xS = entity.getXS();
		float yS = entity.getYS();
		
		Block block = map.getBlock((int) entity.getCenterX(), (int) entity.getCenterY(), (int) z + 1);
		
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
	
	private void interactingWithEntity(Map map, Entity entity) {
		entity.getInteractingEntities().clear();
		
		SquareArea area = entity.getArea();
		
		for (Entity e : map.entities) {
			if (area.isCollided(e.getArea())) {
				entity.getInteractingEntities().add(e);
			}
		}
	}
	
}

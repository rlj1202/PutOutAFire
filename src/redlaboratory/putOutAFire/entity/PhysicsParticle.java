package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Block;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.graphics.Color;

public class PhysicsParticle extends Particle {

	public PhysicsParticle(float x, float y, float z, float xS, float yS, float zS, float size, Color color, int aliveTicks, Map curMap) {
		super(x, y, z, xS, yS, zS, size, color, aliveTicks, curMap);
	}
	
	public void tick() {
		super.tick();
		
		collisionTestMap();
		collisionTestBlock();
		collisionTestEntity();
	}
	
	private void collisionTestMap() {
		if (curMap == null) return;
		
		if (x < 0) {
			x = 0;
			xS = 0;
		}
		
		if (y < 0) {
			y = 0;
			yS = 0;
		}
		
		if (x + width > curMap.getWidth()) {
			x = curMap.getWidth() - width;
			xS = 0;
		}
		
		if (y + height > curMap.getLength()) {
			y = curMap.getLength() - height;
			yS = 0;
		}
	}
	
	private void collisionTestBlock() {
		if (curMap == null) return;
		
		Block block = curMap.getBlock((int) getCenterX(), (int) getCenterY(), (int) z + 1);
		if (block != null) {
			if (!block.isThroughable()) {
				SquareArea particleA = getArea();
				SquareArea blockA = new SquareArea((int) getCenterX(), (int) getCenterY(), 1, 1);
				
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
	}
	
	@SuppressWarnings("unused")
	private void _collisionTestBlock() {
		if (curMap == null) return;
		
		SquareArea playerA = new SquareArea(x, y, width, height);
		
		for (int bY = (int) y; bY <= (int) (y + height); bY++) {
			for (int bX = (int) x; bX <= (int) (x + width); bX++) {
				Block block = curMap.getBlock(bX, bY, (int) z + 1);
				
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
	}
	
	private void collisionTestEntity() {
		if (curMap == null) return;
		
		for (Entity entity : curMap.getEntities()) {
			if (entity.equals(this)) continue;
			if (entity.isThroughable()) continue;
			
			SquareArea particleA = getArea();
			SquareArea entityA = entity.getArea();
			
			SquareArea inter = particleA.getIntersection(entityA);// Intersection
			
			if (inter != null) {// Collision detected
				if (inter.getWidth() > inter.getHeight()) {
					if (particleA.getCenterY() < entityA.getCenterY()) {
						y = y - inter.getHeight();
					} else {
						y = y + inter.getHeight();
					}
					
					yS = 0;
				} else if (inter.getHeight() > inter.getWidth()) {
					if (particleA.getCenterX() < entityA.getCenterX()) {
						x = x - inter.getWidth();
					} else {
						x = x + inter.getWidth();
					}
					
					xS = 0;
				} else {
					if (particleA.getCenterY() < entityA.getCenterY()) {
						y = y - inter.getHeight();
					} else {
						y = y + inter.getHeight();
					}
					
					if (particleA.getCenterX() < entityA.getCenterX()) {
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

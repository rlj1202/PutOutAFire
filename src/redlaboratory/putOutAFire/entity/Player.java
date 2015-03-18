package redlaboratory.putOutAFire.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import redlaboratory.putOutAFire.Block;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.graphics.Texture;
import redlaboratory.putOutAFire.item.Item;

public class Player extends LivingEntity {
	
	private ArrayList<Item> items;
	private Item itemOnHand;
	
	private float walkSpeed = 0.01f;
	private float crouchSpeed = 0.002f;
	
	public Player(float x, float y, float z, float health, float maxHealth) {
		this(x, y, z, health, maxHealth, null);
	}
	
	public Player(float x, float y, float z, float health, float maxHealth, Map curMap) {
		super(0.99f, 0.99f, x, y, z, 0, 0, 0, health, maxHealth, curMap, Texture.CHAR_TEX, true);
		
		items = new ArrayList<Item>();
		itemOnHand = null;
		
		this.health = health;
		this.maxHealth = maxHealth;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item getItemOnHand() {
		return itemOnHand;
	}
	
	public void setItemOnhand(Item item) {
		itemOnHand = item;
	}
	
	@Override
	public void tick() {
		int angle = 0;
		int xD = 0;// Direction
		int yD = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			yD += 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			yD -= 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			xD -= 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			xD += 1;

		if (!(xD == 0 && yD == 0)) {
			if (yD == 0) {
				if (xD == -1) {
					angle = 180;
				} else if (xD == 0) {
					angle = 0;
				} else if (xD == 1) {
					angle = 0;
				}
			} else {
				if (xD == -1) {
					angle = 135 * yD;
				} else if (xD == 0) {
					angle = 90 * yD;
				} else if (xD == 1) {
					angle = 45 * yD;
				}
			}

			if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				xS += Math.cos(Math.toRadians(angle)) * walkSpeed;
				yS += Math.sin(Math.toRadians(angle)) * walkSpeed;
			} else {
				xS += Math.cos(Math.toRadians(angle)) * crouchSpeed;
				yS += Math.sin(Math.toRadians(angle)) * crouchSpeed;
			}
		}
		
		// Player position calculate
		super.tick();
		
		collisionTestMap();
		collisionTestBlock();
		collisionTestEntity();
		interactWithEntity();
		
		if (itemOnHand != null) itemOnHand.tick();
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
			
			SquareArea playerA = new SquareArea(x, y, width, height);
			SquareArea entityA = new SquareArea(entity.x, entity.y, entity.width, entity.height);
			
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
	}
	
	private void interactWithEntity() {
		for (Iterator<Entity> it = curMap.getEntities().iterator(); it.hasNext();) {
			Entity entity = it.next();
			
			if (getArea().isCollided(entity.getArea())) {
				interactingEntity = entity;
				
				if (entity instanceof ConsumableEntity) {
					ConsumableEntity ce = (ConsumableEntity) entity;
					
					if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
						ce.consume(this);
						it.remove();
					}
				}
				
				return;
			}
		}
		
		interactingEntity = null;
	}

}

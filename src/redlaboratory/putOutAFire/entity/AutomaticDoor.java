package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

public class AutomaticDoor extends Entity {
	
	private float minY;
	private float maxY;
	
	public AutomaticDoor(float x, float y, float z) {
		super(0.5f, 2.0f, x, y, z, Texture.AUTOMATIC_DOOR, false, false);
		
		minY = y;
		maxY = y + 2;
	}
	
	@Override
	public void tick(Game game, Map map) {
		SquareArea area = new SquareArea(x - 1.0f, y - 2.0f, width + 2.0f, height + 4.0f);
		
		boolean collide = false;
		for (Entity entity : map.getEntities()) {
			if (!(entity instanceof Player)) continue;
			
			if (area.isCollided(entity.getArea())) {
				collide = true;
			}
		}
		
		if (collide) {
			if (y < maxY) {
				y += 0.02f;
			}
		} else {
			if (y > minY) {
				y -= 0.02f;
			}
		}
	}
	
}

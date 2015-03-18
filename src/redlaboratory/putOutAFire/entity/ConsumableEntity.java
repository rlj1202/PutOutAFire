package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class ConsumableEntity extends Entity {
	
	public ConsumableEntity(float width, float height, float x, float y, float z, Map curMap, Texture texture) {
		super(width, height, x, y, z, curMap, texture, true);
	}
	
	@Override
	abstract public void tick();
	
	/**
	 * @param entity An entity that tried to consume
	 * @return Whether an entity succeed to consume
	 */
	abstract public boolean consume(Entity consumer);
	
}

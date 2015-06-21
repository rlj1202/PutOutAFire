package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class ConsumableEntity extends Entity {
	
	public ConsumableEntity(float width, float height, float x, float y, float z, Texture texture) {
		super(width, height, x, y, z, texture, false, true);
	}
	
	@Override
	abstract public void tick(Game game, Map map);
	
	/**
	 * @param entity An entity that tried to consume
	 * @return Whether an entity succeed to consume
	 */
	abstract public boolean consume(Entity consumer);
	
}

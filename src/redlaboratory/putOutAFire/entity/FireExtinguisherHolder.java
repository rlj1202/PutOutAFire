package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;
import redlaboratory.putOutAFire.item.FireExtinguisher;

public class FireExtinguisherHolder extends ConsumableEntity {
	
	public FireExtinguisherHolder(float size, float x, float y, float z) {
		super(size, size * 1.714f, x, y, z, Texture.FIRE_EXTINGUISHER);
	}
	
	@Override
	public void tick(Game game, Map map) {
		
	}

	@Override
	public boolean consume(Entity consumer) {
		if (consumer instanceof Player) {
			Player player = (Player) consumer;
			
			if (player.getItemOnHand() == null) {
				player.setItemOnhand(new FireExtinguisher(0.5f, 10000));
				
				return true;
			}
		}
		
		return false;
	}
	
}

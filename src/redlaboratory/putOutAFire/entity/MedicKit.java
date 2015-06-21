package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

public class MedicKit extends ConsumableEntity {
	
	private float capacity;// ¿ë·®
	
	public MedicKit(float size, float x, float y, float z, float capacity) {
		super(size, size, x, y, z, Texture.MEDIC_KIT);
		
		this.capacity = capacity;
	}
	
	@Override
	public void tick(Game game, Map map) {
		
	}
	
	@Override
	public boolean consume(Entity consumer) {
		if (consumer instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) consumer;
			
			if (le.getHealth() < le.getMaxHealth()) {
				le.setHealth(le.getHealth() + capacity);
				
				return true;
			}
		}
		
		return false;
	}

}

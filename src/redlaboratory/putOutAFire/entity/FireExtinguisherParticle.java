package redlaboratory.putOutAFire.entity;

import java.util.List;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Color;

public class FireExtinguisherParticle extends Particle {

	public FireExtinguisherParticle(float x, float y, float z, float xS, float yS, int aliveTicks) {
		super(x, y, z, xS, yS, 0, true, 0.05f, Color.WHITE, aliveTicks);
	}
	
	@Override
	public void tick(Game game, Map map) {
		super.tick(game, map);
		
		List<Entity> interactingEntities = getInteractingEntities();
		
		if (interactingEntities == null) return;
		
		for (Entity entity : interactingEntities) {
			if (entity instanceof Fire) {
				Fire fire = (Fire) entity;
				
				float centerX = fire.getCenterX();
				
				fire.setSize(fire.getSize() - 0.0001f);
				fire.setCenterX(centerX);
				
				if (fire.getSize() < 0.5f) {
					map.removeEntity(fire);
					fire.getAudioClip().stop();
				}
			}
		}
	}
	
}

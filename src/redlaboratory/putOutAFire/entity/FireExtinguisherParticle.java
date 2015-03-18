package redlaboratory.putOutAFire.entity;

import java.util.Iterator;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.graphics.Color;

public class FireExtinguisherParticle extends PhysicsParticle {

	public FireExtinguisherParticle(float x, float y, float z, float xS, float yS, int aliveTicks, Map curMap) {
		super(x, y, z, xS, yS, 0, 0.05f, Color.WHITE, aliveTicks, curMap);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		for (Iterator<Entity> it = curMap.getEntities().iterator(); it.hasNext();) {
			Entity entity = it.next();
			
			if (entity instanceof Fire) {
				Fire fire = (Fire) entity;
				
				if (getArea().isCollided(fire.getArea())) {
					float cX = fire.getCenterX();
					
					fire.setSize(fire.getSize() - 0.0001f);
					fire.setCenterX(cX);
					
					if (fire.getSize() < 0.5f) {
						it.remove();
						fire.getAudioClip().stop();
					}
				}
			}
		}
	}

}

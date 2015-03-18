package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.graphics.Color;

public class Particle extends MovingEntity {
	
	private Color color;
	private int aliveTicks;
	
	public Particle(float x, float y, float z, float xS, float yS, float zS, float size, Color color, int aliveTicks, Map curMap) {
		super(size, size, x, y, z, xS, yS, zS, curMap, null, true);
		
		this.color = color;
		this.aliveTicks = aliveTicks;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getAliveTicks() {
		return aliveTicks;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (aliveTicks > 0) {
			aliveTicks--;
		} else {
			curMap.removeEntity(this);
		}
	}
	
}

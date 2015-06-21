package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Color;

public class Particle extends Entity {
	
	private Color color;
	private int aliveTicks;
	
	public Particle(float x, float y, float z, float xS, float yS, float zS, boolean physicsable, float size, Color color, int aliveTicks) {
		super(size, size, x, y, z, xS, yS, zS, null, physicsable, true);
		
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
	public void tick(Game game, Map map) {
		if (aliveTicks > 0) aliveTicks--;
		else map.removeEntity(this);
	}
	
}

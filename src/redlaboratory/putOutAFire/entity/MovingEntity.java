package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Block;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.graphics.Texture;

public class MovingEntity extends Entity {
	
	protected float xS;
	protected float yS;
	protected float zS;
	
	public MovingEntity(float width, float height, float x, float y, float z, float xS, float yS, float zS, Map curMap, Texture texture, boolean throughable) {
		super(width, height, x, y, z, curMap, texture, throughable);
		
		this.xS = xS;
		this.yS = yS;
		this.zS = zS;
	}
	
	public float getXS() {
		return xS;
	}
	
	public float getYS() {
		return yS;
	}
	
	public float getZS() {
		return zS;
	}
	
	public void setXS(float xS) {
		this.xS = xS;
	}
	
	public void setYS(float yS) {
		this.yS = yS;
	}
	
	public void setZS(float zS) {
		this.zS = zS;
	}
	
	public void tick() {
		x += xS;
		y += yS;
		z += zS;
		
		if (curMap != null) {
			Block block = curMap.getBlock((int) (x + width / 2), (int) (y + height / 2), (int) z);
			
			if (block != null) {
				xS *= block.getFrictionalForce();
				yS *= block.getFrictionalForce();
				zS *= block.getFrictionalForce();
			}
		}
	}
	
}

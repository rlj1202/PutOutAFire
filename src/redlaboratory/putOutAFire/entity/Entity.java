package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class Entity {
	
	protected Map curMap;
	
	protected float width;
	protected float height;
	
	protected float x;
	protected float y;
	protected float z;
	
	private Texture texture;
	
	protected boolean throughable;
	
	public Entity(float width, float height, float x, float y, float z, Map curMap, Texture texture, boolean throughable) {
		this.width = width;
		this.height = height;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.curMap = curMap;
		
		this.texture = texture;
		
		this.throughable = throughable;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getCenterX() {
		return x + (width / 2);
	}
	
	public float getCenterY() {
		return y + (height / 2);
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Map getCurrentMap() {
		return curMap;
	}
	
	public SquareArea getArea() {
		return new SquareArea(x, y, width, height);
	}
	
	public boolean isThroughable() {
		return throughable;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	public void setCenterX(float x) {
		this.x = x - width / 2;
	}
	
	public void setCenterY(float y) {
		this.y = y - height / 2;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setCurrentMap(Map curMap) {
		this.curMap = curMap;
	}
	
	abstract public void tick();
	
}

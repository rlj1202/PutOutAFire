package redlaboratory.putOutAFire.entity;

import java.util.LinkedList;
import java.util.List;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class Entity {
	
	protected float width;
	protected float height;
	
	protected float x;
	protected float y;
	protected float z;
	
	protected float xS;
	protected float yS;
	protected float zS;
	
	private Texture texture;
	
	protected boolean physicsable;
	protected boolean throughable;
	
	protected List<Entity> interactingEntities;
	
	public Entity(float width, float height, float x, float y, float z, Texture texture, boolean physicsable, boolean throughable) {
		this(width, height, x, y, z, 0, 0, 0, texture, physicsable, throughable);
	}
	
	public Entity(float width, float height, float x, float y, float z, float xS, float yS, float zS, Texture texture, boolean physicsable, boolean throughable) {
		this.width = width;
		this.height = height;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.xS = xS;
		this.yS = yS;
		this.zS = zS;
		
		this.texture = texture;
		
		this.physicsable = physicsable;
		this.throughable = throughable;
		
		interactingEntities = new LinkedList<Entity>();
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
	
	public float getXS() {
		return xS;
	}
	
	public float getYS() {
		return yS;
	}
	
	public float getZS() {
		return zS;
	}
	
	public List<Entity> getInteractingEntities() {
		return interactingEntities;
	}

	public Texture getTexture() {
		return texture;
	}
	
	public SquareArea getArea() {
		return new SquareArea(x, y, width, height);
	}
	
	public boolean isPhysicsable() {
		return physicsable;
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
	
	public void setXS(float xS) {
		this.xS = xS;
	}
	
	public void setYS(float yS) {
		this.yS = yS;
	}
	
	public void setZS(float zS) {
		this.zS = zS;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	abstract public void tick(Game game, Map map);
	
}

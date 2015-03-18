package redlaboratory.putOutAFire.item;

import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class Item {
	
	protected Entity curEntity;
	
	protected float x;
	protected float y;
	protected float z;
	
	protected float width;
	protected float height;
	
	private Texture texture;
	
	private String title;
	
	public Item(float x, float y, float z, float width, float height, Entity curEntity, Texture texture, String title) {
		this.width = width;
		this.height = height;
		this.texture = texture;
		
		this.curEntity = curEntity;
		
		this.title = title;
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
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public Entity getCurrentEntity() {
		return curEntity;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setCurrentEntity(Entity curEntity) {
		this.curEntity = curEntity;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	abstract public void tick();
	
}

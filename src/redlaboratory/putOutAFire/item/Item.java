package redlaboratory.putOutAFire.item;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class Item {
	
	protected float x;
	protected float y;
	protected float z;
	
	protected float width;
	protected float height;
	
	private Texture texture;
	
	private String title;
	
	public Item(float x, float y, float z, float width, float height, Texture texture, String title) {
		this.width = width;
		this.height = height;
		this.texture = texture;
		
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
	
	public Texture getTexture() {
		return texture;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	abstract public void tick(Game game, Map map, Entity entity);
	
}

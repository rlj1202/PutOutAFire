package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.graphics.Texture;

abstract public class LivingEntity extends Entity {
	
	protected int noDamageTick;
	
	protected float health;
	protected float maxHealth;
	
	public LivingEntity(float width, float height, float x, float y, float z, float xS, float yS, float zS, float health, float maxHealth, Texture texture, boolean throughable) {
		super(width, height, x, y, z, xS, yS, zS, texture, true, throughable);
		
		this.health = health;
		this.maxHealth = maxHealth;
	}
	
	public float getHealth() {
		return health;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public int getNoDamageTick() {
		return noDamageTick;
	}
	
	public void setHealth(float health) {
		if (health < 0) health = 0;
		if (health > maxHealth) health = maxHealth;
		
		this.health = health;
	}
	
	public void setMaxHealth(float maxHealth) {
		if (maxHealth < 0) return;
		
		this.maxHealth = maxHealth;
		
		if (maxHealth < health) health = maxHealth;
	}
	
	public void setNoDamageTick(int noDamageTick) {
		this.noDamageTick = noDamageTick;
	}
	
}

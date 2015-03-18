package redlaboratory.putOutAFire.entity;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.graphics.Texture;

abstract public class LivingEntity extends MovingEntity {
	
	protected int noDamageTick;
	
	protected float health;
	protected float maxHealth;
	
	protected Entity interactingEntity;
	
	public LivingEntity(float width, float height, float x, float y, float z, float xS, float yS, float zS, float health, float maxHealth, Map curMap, Texture texture, boolean throughable) {
		super(width, height, x, y, z, xS, yS, zS, curMap, texture, throughable);
		
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
	
	public Entity getInteractingEntity() {
		return interactingEntity;
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
	
	public void setInteractingEntity(Entity entity) {
		interactingEntity = entity;
	}
	
}

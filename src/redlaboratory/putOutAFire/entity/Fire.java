package redlaboratory.putOutAFire.entity;

import java.util.List;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.area.SquareArea;
import redlaboratory.putOutAFire.audios.Audio;
import redlaboratory.putOutAFire.audios.AudioClip;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

public class Fire extends Entity {
	
	private float size;
	
	private AudioClip audio;
	
	public Fire(float size, float x, float y, float z) {
		super(size, size, x, y, z, Texture.FIRE, false, true);
		
		this.size = size;
		audio = new AudioClip(Audio.TEST_SOUND, x, y, z, 1.0f, 1.0f, true);
		audio.play();
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		
		audio.setLocation(x, y, z);
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		
		audio.setLocation(x, y, z);
	}
	
	@Override
	public void setZ(float z) {
		super.setZ(z);
		
		audio.setLocation(x, y, z);
	}
	
	public float getSize() {
		return size;
	}
	
	public void setSize(float size) {
		this.size = size;
		
		this.width = size;
		this.height = size;
	}
	
	public AudioClip getAudioClip() {
		return audio;
	}
	
	@Override
	public void tick(Game game, Map map) {
		this.width = size;
		this.height = size;
		
		List<Entity> interactingEntities = getInteractingEntities();
		
		if (interactingEntities == null) return;
		
		for (Entity entity : interactingEntities) {
			if (entity instanceof LivingEntity) {
				LivingEntity lentity = (LivingEntity) entity;
				
				SquareArea area = getArea();
				SquareArea entityA = lentity.getArea();
				
				SquareArea inter = area.getIntersection(entityA);// Intersection
				
				if (inter != null) {// Collision detected
					if (inter.getWidth() > inter.getHeight()) {
						if (area.getCenterY() < entityA.getCenterY()) {
							lentity.setYS(0.04f);
						} else {
							lentity.setYS(-0.04f);
						}
					} else if (inter.getHeight() > inter.getWidth()) {
						if (area.getCenterX() < entityA.getCenterX()) {
							lentity.setXS(0.04f);
						} else {
							lentity.setXS(-0.04f);
						}
					} else {
						if (area.getCenterY() < entityA.getCenterY()) {
							lentity.setYS(0.04f);
						} else {
							lentity.setYS(-0.04f);
						}
						
						if (area.getCenterX() < entityA.getCenterX()) {
							lentity.setXS(0.04f);
						} else {
							lentity.setXS(-0.04f);
						}
					}
					
					lentity.setHealth(lentity.getHealth() - size);
				}
			}
		}
	}
	
}

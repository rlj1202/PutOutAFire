package redlaboratory.putOutAFire.item;

import org.lwjgl.input.Mouse;

import redlaboratory.putOutAFire.Camera;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.entity.FireExtinguisherParticle;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;

public class FireExtinguisher extends Item {
	
	private float size;
	private int capacity;
	
	public FireExtinguisher(float size, int capacity) {
		this(size, capacity, "Fire Extinguisher");
	}
	
	public FireExtinguisher(float size, int capacity, String title) {
		super(0.1f, -0.1f, 0.1f, size, size * 1.714f, Texture.FIRE_EXTINGUISHER, title);
		
		this.size = size;
		this.capacity = capacity;
	}
	
	public float getSize() {
		return size;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public void tick(Game game, Map map, Entity entity) {
		if (Mouse.isButtonDown(0)) {
			float itemX = entity.getX() + entity.getWidth() + x;
			float itemY = entity.getY() + y;
			
			Camera camera = game.getCamera();
			
			float entityDX = camera.ZOOM * itemX - camera.X;
			float entityDY = camera.ZOOM * itemY - camera.Y;
			float mouseDX = Mouse.getX();
			float mouseDY = Mouse.getY();
			
			double angle = Math.atan2(mouseDY - entityDY, mouseDX - entityDX);
			angle += Math.toRadians((Math.random() - 0.5f) * 25);
			
			float entityXS = entity.getXS();
			float entityYS = entity.getYS();
			
			for (int i = 0; i < 5; i++) {
				if (capacity > 0) {
					FireExtinguisherParticle particle = new FireExtinguisherParticle(
							itemX + (float) (Math.random() - 0.5f) * 0.1f,
							itemY + (float) (Math.random() - 0.5f) * 0.1f,
							entity.getZ(),
							(float) (Math.cos(angle) * 0.2f) + entityXS,
							(float) (Math.sin(angle) * 0.2f) + entityYS,
							(int) ((Math.random() + 0.5f) * 30));
					
					map.addEntity(particle);
					
					capacity--;
				} else {
					break;
				}
			}
		}
	}
	
}

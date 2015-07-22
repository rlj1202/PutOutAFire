package redlaboratory.putOutAFire.game;

import java.util.ArrayList;

import redlaboratory.putOutAFire.Camera;
import redlaboratory.putOutAFire.Core;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.graphics.Renderer;

abstract public class Game {
	
	private Map map;
	private Camera camera;
	private Entity viewEntity;
	
	private ArrayList<Renderer> renderers;
	
	public Game() {
		camera = new Camera();
		renderers = new ArrayList<Renderer>();
	}
	
	public Map getMap() {
		return map;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Entity getViewEntity() {
		return viewEntity;
	}
	
	public ArrayList<Renderer> getRenderers() {
		return renderers;
	}
	
	public float[] getListenerPosition() {
		return new float[] {viewEntity.getCenterX(), viewEntity.getCenterY(), viewEntity.getZ()};
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void setViewEntity(Entity viewEntity) {
		this.viewEntity = viewEntity;
	}
	
	public void setRenderers(ArrayList<Renderer> renderers) {
		this.renderers = renderers;
	}
	
	public void addRenderer(Renderer renderer) {
		renderers.add(renderer);
	}
	
	abstract public void initialize();
	
	abstract public void tick(Core core);
	
}

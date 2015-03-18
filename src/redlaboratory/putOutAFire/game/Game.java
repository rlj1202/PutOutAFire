package redlaboratory.putOutAFire.game;

import java.util.ArrayList;

import redlaboratory.putOutAFire.Camera;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.graphics.Renderer;

abstract public class Game {
	
	private Map map;
	private Camera camera;
	private Entity viewEntity;
	
	private ArrayList<Renderer> renderers;
	
	public Game() {
		initialize();
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
	
	public void initialize() {
		camera = new Camera();
		renderers = new ArrayList<Renderer>();
	}
	
	abstract public void tick();
	
}

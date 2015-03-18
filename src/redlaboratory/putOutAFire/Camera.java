package redlaboratory.putOutAFire;

public class Camera {
	
	public float X;
	public float Y;
	public int ZOOM;
	
	public Camera() {
		this(0, 0, 8 * 8);
	}
	
	public Camera(float x, float y, int zoom) {
		X = x;
		Y = y;
		ZOOM = zoom;
	}
	
}

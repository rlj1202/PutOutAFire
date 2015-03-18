package redlaboratory.putOutAFire.area;


public class PointArea implements Area {
	
	private float x;
	private float y;
	
	public PointArea(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean isCollide(SquareArea area) {
		if (area.getX() < x && x < area.getX() + area.getWidth()) {
			if (area.getY() < y && y < area.getY() + area.getHeight()) {
				return true;
			}
		}
		
		return false;
	}
	
}

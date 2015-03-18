package redlaboratory.putOutAFire.area;


public class CircleArea implements Area {
	
	private float x;
	private float y;
	private float radius;
	
	public CircleArea(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public boolean isCollide(CircleArea area) {
		if (Math.sqrt(Math.pow(x - area.x, 2) + Math.pow(y - area.y, 2)) < radius + area.radius) return true;
		else return false;
	}
	
	public boolean isCollide(SquareArea area) {
		SquareArea circleSquareArea = new SquareArea(x - radius, y - radius, radius * 2, radius * 2);
		
		if (area.isCollided(circleSquareArea)) {
			SquareArea inter = area.getIntersection(circleSquareArea);
			
			float[] point1 = new float[] {inter.getX(), inter.getY()};
			float[] point2 = new float[] {inter.getX() + inter.getWidth(), inter.getY()};
			float[] point3 = new float[] {inter.getX() + inter.getWidth(), inter.getY() + inter.getHeight()};
			float[] point4 = new float[] {inter.getX(), inter.getY() + inter.getHeight()};
			
			float distance1 = (float) Math.sqrt(Math.pow(Math.abs(x - point1[0]), 2) + Math.pow(Math.abs(y - point1[1]), 2));
			float distance2 = (float) Math.sqrt(Math.pow(Math.abs(x - point2[0]), 2) + Math.pow(Math.abs(y - point2[1]), 2));
			float distance3 = (float) Math.sqrt(Math.pow(Math.abs(x - point3[0]), 2) + Math.pow(Math.abs(y - point3[1]), 2));
			float distance4 = (float) Math.sqrt(Math.pow(Math.abs(x - point4[0]), 2) + Math.pow(Math.abs(y - point4[1]), 2));
			
			if (radius > distance1 ||
					radius > distance2 ||
					radius > distance3 ||
					radius > distance4) {
				return true;
			}
		}
		
		return false;
	}
	
}

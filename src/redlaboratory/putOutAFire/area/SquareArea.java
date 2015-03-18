package redlaboratory.putOutAFire.area;

import java.util.Arrays;

public class SquareArea implements Area {
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	public SquareArea(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getCenterX() {
		return x + width / 2;
	}
	
	public float getCenterY() {
		return y + height / 2;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public SquareArea getIntersection(SquareArea area) {
		float[] xs = new float[] {
				x,
				x + width,
				area.x,
				area.x + area.width};
		float[] ys = new float[] {
				y,
				y + height,
				area.y,
				area.y + area.height};
		
		if ((xs[2] < xs[0] && xs[0] < xs[3]) ||
				(xs[2] < xs[1] && xs[1] < xs[3]) ||
				(xs[0] < xs[2] && xs[2] < xs[1]) ||
				(xs[0] < xs[3] && xs[3] < xs[1])) {
			if ((ys[2] < ys[0] && ys[0] < ys[3]) ||
					(ys[2] < ys[1] && ys[1] < ys[3]) ||
					(ys[0] < ys[2] && ys[2] < ys[1]) ||
					(ys[0] < ys[3] && ys[3] < ys[1])) {
				// Sort xs, ys
				for (int a = 0; a < 4; a++) {
					for (int i = 0; i < 3; i++) {
						if (xs[i] > xs[i + 1]) {
							float temp = xs[i];
							xs[i] = xs[i + 1];
							xs[i + 1] = temp;
						}
						
						if (ys[i] > ys[i + 1]) {
							float temp = ys[i];
							ys[i] = ys[i + 1];
							ys[i + 1] = temp;
						}
					}
				}
				
				return new SquareArea(xs[1], ys[1], xs[2] - xs[1], ys[2] - ys[1]);
			}
		}
		
		return null;
	}
	
	public boolean isCollided(SquareArea area) {
		float[] xs = new float[] {
				x,
				x + width,
				area.x,
				area.x + area.width};
		float[] ys = new float[] {
				y,
				y + height,
				area.y,
				area.y + area.height
		};
		
		if ((xs[2] < xs[0] && xs[0] < xs[3]) ||
				(xs[2] < xs[1] && xs[1] < xs[3]) ||
				(xs[0] < xs[2] && xs[2] < xs[1]) ||
				(xs[0] < xs[3] && xs[3] < xs[1])) {
			if ((ys[2] < ys[0] && ys[0] < ys[3]) ||
					(ys[2] < ys[1] && ys[1] < ys[3]) ||
					(ys[0] < ys[2] && ys[2] < ys[1]) ||
					(ys[0] < ys[3] && ys[3] < ys[1])) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(new float[] {x, y, width, height});
	}
	
}

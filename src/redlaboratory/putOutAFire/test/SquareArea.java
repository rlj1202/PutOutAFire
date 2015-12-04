package redlaboratory.putOutAFire.test;

public class SquareArea {
	
	@SuppressWarnings("unused")
	private float x;
	@SuppressWarnings("unused")
	private float y;
	@SuppressWarnings("unused")
	private float z;
	@SuppressWarnings("unused")
	private float size;
	@SuppressWarnings("unused")
	private float height;
	
	public SquareArea(float x, float y, float z, float size, float height) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
		this.height = height;
	}
	
	public float[] getIntersection(SquareArea area) {
		
		return null;
	}
	
	public static float[] getIntersection(float[] data) {
		/*
		 * [x, y, z, size, height,
		 *   x, y, z, size, height]
		 * 
		 * [0, 1, 2, 3   , 4     ,
		 *   5, 6, 7, 8   , 9     ]
		 */
		if (Math.abs(data[5] - data[0]) < data[3] + data[8]) {
			if (Math.abs(data[7] - data[2]) < data[3] + data[8]) {
				if (data[1] < data[6] + data[9] && data[6] < data[1] + data[4]) {
					
				}
			}
		}
		
		return null;
	}
	
}

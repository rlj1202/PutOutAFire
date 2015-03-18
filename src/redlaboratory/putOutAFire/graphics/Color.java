package redlaboratory.putOutAFire.graphics;

public class Color {
	
	public static Color WHITE;
	public static Color BLACK;
	public static Color RED;
	public static Color GREEN;
	public static Color BLUE;
	
	public static Color DAMAGE_EFFECT;
	
	static {
		WHITE = new Color(1, 1, 1);
		BLACK = new Color(0, 0, 0);
		RED = new Color(1, 0, 0);
		GREEN = new Color(0, 1, 0);
		BLUE = new Color(0, 0, 1);
		
		DAMAGE_EFFECT = new Color(1, 0, 0, 0.7f);
	}
	
	public float R;
	public float G;
	public float B;
	public float ALPHA;
	
	public Color(float r, float g, float b) {
		R = r;
		G = g;
		B = b;
		ALPHA = 1;
	}
	
	public Color(float r, float g, float b, float alpha) {
		R = r;
		G = g;
		B = b;
		ALPHA = alpha;
	}
	
}

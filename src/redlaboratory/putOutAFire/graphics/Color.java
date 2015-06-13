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
	
	@Override
	public int hashCode() {
		return hashCode(R, G, B, ALPHA);
	}
	
	public static int hashCode(float r, float g, float b, float alpha) {
		int rB = (byte) (r / 0xff) << 24;
		int gB = (byte) (g / 0xff) << 16;
		int bB = (byte) (b / 0xff) << 8;
		int alphaB = (byte) (alpha / 0xff);
		
		return rB + gB + bB + alphaB;
	}

	public static Color valueOf(float r, float g, float b) {
		return valueOf(r, g, b, 1);
	}
	
	public static Color valueOf(float r, float g, float b, float alpha) {
		return new Color(r, g, b, alpha);
	}
	
}

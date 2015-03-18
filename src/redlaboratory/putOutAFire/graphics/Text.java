package redlaboratory.putOutAFire.graphics;

public class Text {
	
	public float X;
	public float Y;
	public float SIZE;
	public Color COLOR;
	public String TEXT;
	public TextRenderAttribute ATTRIBUTE;
	
	public Text(float x, float y, float size, Color color, String text, TextRenderAttribute attribute) {
		X = x;
		Y = y;
		SIZE = size;
		COLOR = color;
		TEXT = text;
		ATTRIBUTE = attribute;
	}
	
}

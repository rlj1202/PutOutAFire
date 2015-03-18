package redlaboratory.putOutAFire.utils;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import redlaboratory.putOutAFire.graphics.Color;
import redlaboratory.putOutAFire.graphics.Render;
import redlaboratory.putOutAFire.graphics.Text;
import redlaboratory.putOutAFire.graphics.TextRenderAttribute;

public class SubtitleManager {
	
	private ArrayList<Subtitle> subtitles;
	
	public SubtitleManager() {
		subtitles = new ArrayList<Subtitle>();
	}
	
	public void addSubtitle(Text text, int ticks) {
		subtitles.add(new Subtitle(text, ticks));
	}
	
	public void addSubtitle(String text, float size, int ticks) {
		addSubtitle(text, size, Color.WHITE, TextRenderAttribute.CENTER, ticks);
	};
	
	public void addSubtitle(String text, float size, Color color, TextRenderAttribute attribute, int ticks) {
		subtitles.add(new Subtitle(new Text(0, 0, size, color, text, attribute), ticks));
	}
	
	public void tick(Render render) {
		for (Subtitle subtitle : subtitles) {
			if (subtitle.TICKS > 0) {
				render.drawText(Display.getWidth() / 2, Display.getHeight() / 6, subtitle.TEXT.SIZE, subtitle.TEXT.COLOR, subtitle.TEXT.TEXT, subtitle.TEXT.ATTRIBUTE);
				subtitle.TICKS--;
				
				break;
			}
		}
	}
	
	private static class Subtitle {
		
		private Text TEXT;
		private int TICKS;
		
		public Subtitle(Text text, int ticks) {
			TEXT = text;
			TICKS = ticks;
		}
		
	}
	
}

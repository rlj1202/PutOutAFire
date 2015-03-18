package redlaboratory.putOutAFire;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import redlaboratory.putOutAFire.graphics.FontModule;
import redlaboratory.putOutAFire.graphics.Render;
import redlaboratory.putOutAFire.graphics.Texture;
import redlaboratory.putOutAFire.utils.GraphicHelper;

public class Main {
	
	private static boolean eclipse = false;
	
	private static boolean fullScreen = false;
	private static boolean vsync = false;
	private static boolean resizable = false;
	private static boolean antiAliasing = false;
	private static int width = 800;
	private static int height = 600;
	
	public static void main(String[] args) {
		for (String str : args) {
			if (str.equalsIgnoreCase("-eclipse")) {
				eclipse = true;
			} else if (str.equalsIgnoreCase("-fullScreen")) {
				fullScreen = true;
			} else if (str.equalsIgnoreCase("-vsync")) {
				vsync = true;
			} else if (str.equalsIgnoreCase("-resizable")) {
				resizable = true;
			} else if (str.equalsIgnoreCase("-antiAliasing")) {
				antiAliasing = true;
			} else if (str.startsWith("-width:")) {
				width = Integer.parseInt(str.substring(str.lastIndexOf(':') + 1));
			} else if (str.startsWith("-height:")) {
				height = Integer.parseInt(str.substring(str.lastIndexOf(':') + 1));
			}
		}
		
		if (!eclipse) {
			String os = System.getProperty("os.name").toLowerCase();
			
			if (os.indexOf("win") >= 0) {
				System.setProperty("org.lwjgl.librarypath", new File("native/windows").getAbsolutePath());
			} else if (os.indexOf("mac") >= 0) {
				System.setProperty("org.lwjgl.librarypath", new File("native/macosx").getAbsolutePath());
			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0) {
				System.setProperty("org.lwjgl.librarypath", new File("native/linux").getAbsolutePath());
			} else if (os.indexOf("sunos") >= 0) {
				System.setProperty("org.lwjgl.librarypath", new File("native/solaris").getAbsolutePath());
			} else {
				System.setProperty("org.lwjgl.librarypath", new File("native/freebsd").getAbsolutePath());
			}
		}
		
		initialize();
		gameLoop();
		cleanUp();
	}
	
	private static void initialize() {
		setDisplayMode(width, height, fullScreen);
		
		try {
			if (antiAliasing) Display.create(new PixelFormat(32, 8, 16, 1, 4));
			else Display.create(new PixelFormat(32, 8, 16, 1, 0));
			Keyboard.create();
			Mouse.create();
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		Display.setIcon(new ByteBuffer[] {
				GraphicHelper.getByteBuffer(GraphicHelper.getBufferedImage("/redlaboratory/putOutAFire/resources/images/" + "icon_16.png")),
				GraphicHelper.getByteBuffer(GraphicHelper.getBufferedImage("/redlaboratory/putOutAFire/resources/images/" + "icon_32.png"))
		});
		Display.setTitle("Put out a fire!");
		Display.setVSyncEnabled(vsync);
		Display.setResizable(resizable);
		Render.initialize();
		FontModule.initialize();
		
		setMouseCursor(GraphicHelper.getBufferedImage("/redlaboratory/putOutAFire/resources/images/" + "mouseCursor.png"));
	}
	
	private static void gameLoop() {
		int fps = 0;
		long totalTime = 0;
		
		while (!Display.isCloseRequested()) {
			long start = System.nanoTime();
			Core.tick();
			Texture.tick();
			Core.FPS_2 = 1000000000.0f / (System.nanoTime() - start);
			
			Display.update();
			Display.sync(60);
			
			{
				if (Display.wasResized()) Render.initialize();
			}
			
			{
				long spendTime = System.nanoTime() - start;
				
				fps++;
				totalTime += spendTime;
				
				if (totalTime >= 1000000000) {
					Core.FPS_1 = fps;
					
					totalTime = 0;
					fps = 0;
				}
			}
		}
	}
	
	private static void cleanUp() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		AL.destroy();
	}
	
	/**
	 * Set the display mode to be used
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	public static void setDisplayMode(int width, int height, boolean fullscreen) {
		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && 
				(Display.getDisplayMode().getHeight() == height) &&
				(Display.isFullscreen() == fullscreen)) {
			return;
		}
		
		try {
			DisplayMode targetDisplayMode = null;
			
			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];
					
					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
						
						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
								(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}
			
			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}
			
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height+ " fullscreen=" + fullscreen + e);
		}
	}
	
	public static void setMouseCursor(BufferedImage image) {
		try {
			Mouse.setNativeCursor(new Cursor(image.getWidth(), image.getHeight(), 0, image.getHeight() - 1, 1, GraphicHelper.getIntBuffer(image), null));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
}

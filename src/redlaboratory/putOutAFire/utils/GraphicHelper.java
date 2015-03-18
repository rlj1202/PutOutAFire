package redlaboratory.putOutAFire.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import redlaboratory.putOutAFire.Main;

public class GraphicHelper {
	
	public static BufferedImage getBufferedImage(String name) {
		try {
			return ImageIO.read(Main.class.getResource(name));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ByteBuffer getByteBuffer(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[x + y * image.getWidth()];
				buffer.put((byte) ((pixel >> 16) & 0xff));
				buffer.put((byte) ((pixel >> 8) & 0xff));
				buffer.put((byte) (pixel & 0xff));
				buffer.put((byte) ((pixel >> 24) & 0xff));
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer getIntBuffer(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		IntBuffer buffer = BufferUtils.createIntBuffer(image.getWidth() * image.getHeight());
		
		for (int y = image.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[x + y * image.getWidth()];
				buffer.put(pixel);
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
	
}

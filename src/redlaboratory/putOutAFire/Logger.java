package redlaboratory.putOutAFire;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String info(String str) {
		String message = time() + " [INFO] " + str;
		System.out.println(message);
		
		return message;
	}
	
	public static String warning(String str) {
		String message = time() + " [WARNING] " + str;
		System.out.println(message);
		
		return message;
	}
	
	public static String error(String str) {
		String message = time() + " [ERROR] " + str;
		System.out.println(message);
		
		return message;
	}
	
	private static String time() {
		return format.format(new Date(System.currentTimeMillis()));
	}
	
}

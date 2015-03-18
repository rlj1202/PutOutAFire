package redlaboratory.putOutAFire.audios;

import static org.lwjgl.openal.AL10.*;

import org.lwjgl.util.WaveData;

public class Audio {
	
	public static int TEST_SOUND;
	
	static {
		TEST_SOUND = uploadAudio("test_fire.wav");
	}
	
	public static int uploadAudio(String name) {
		WaveData data = WaveData.create("redlaboratory/putOutAFire/resources/audios/" + name);
		int buffer = alGenBuffers();
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		
		return buffer;
	}
	
}

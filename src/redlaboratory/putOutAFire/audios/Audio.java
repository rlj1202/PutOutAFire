package redlaboratory.putOutAFire.audios;

import static org.lwjgl.openal.AL10.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import redlaboratory.putOutAFire.Core;
import redlaboratory.putOutAFire.Logger;

public class Audio {
	
	private static IntBuffer buffer = BufferUtils.createIntBuffer(1);
	
	public static int TEST_SOUND = 0;
	
	private final int source;
	private FloatBuffer position = null;
	private FloatBuffer velocity = null;
	private FloatBuffer orientation = null;
	
	public Audio(int bufferIndex, float x, float y, float z, float volume, float pitch) {
		position = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] {x, y, z}).rewind();
		velocity = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] {0, 0, 0}).rewind();
		orientation = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[] {0, 0, 0, 0, 0, 0}).rewind();
		
		source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer.get(bufferIndex));
		alSourcef(source, AL_GAIN, volume);
		alSourcef(source, AL_PITCH, pitch);
		
		alSource(source, AL_POSITION, position);
		alSource(source, AL_VELOCITY, velocity);
		alSource(source, AL_ORIENTATION, orientation);
		
//		Logger.info(source + " has been created.");
	}
	
	public static void initialize() {
		alGenBuffers(buffer);
		
		int errorCode = alGetError();
		if (errorCode != AL_NO_ERROR) {
			Logger.warning("Loading audioes have had some problems. (ErrorCode: " + errorCode + ")");
			
			return;
		}
		
		uploadBuffer(TEST_SOUND, "test_fire.wav");
		
		Logger.info("Audioes have been loaded successfully.");
	}
	
	public static void tick(Core core) {
		float[] listenerPos = core.getListenerPosition();
		
		alListener3f(AL10.AL_POSITION, listenerPos[0], listenerPos[1], listenerPos[2]);
	}
	
	private static void uploadBuffer(int index, String name) {
		WaveData data = WaveData.create("redlaboratory/putOutAFire/resources/audios/" + name);
		alBufferData(buffer.get(index), data.format, data.data, data.samplerate);
		data.dispose();
	}
	
	public void play(boolean loop) {
		alSourcei(source, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
		alSourcePlay(source);
		
//		Logger.info(source + " has been played.");
	}
	
	public void pause() {
		alSourcePause(source);
		
//		Logger.info(source + " has been paused.");
	}
	
	public void stop() {
		alSourceStop(source);
		
//		Logger.info(source + " has been stopped.");
	}
	
	public void rewind() {
		alSourceRewind(source);
	}
	
	public boolean isInitialized() {
		int state = alGetSourcei(source, AL_SOURCE_STATE);
		
		return state == AL_INITIAL;
	}
	
	public boolean isPlaying() {
		int state = alGetSourcei(source, AL_SOURCE_STATE);
		
		return state == AL_PLAYING;
	}
	
	public boolean isPaused() {
		int state = alGetSourcei(source, AL_SOURCE_STATE);
		
		return state == AL_PAUSED;
	}
	
	public boolean isStopped() {
		int state = alGetSourcei(source, AL_SOURCE_STATE);
		
		return state == AL_STOPPED;
	}
	
	public void setLocation(float x, float y, float z) {
		position.clear();
		
		alSource(source, AL_POSITION, (FloatBuffer) position.put(new float[] {x, y, z}).rewind());
	}
	
	public void setVolume(float volume) {
		alSourcef(source, AL_GAIN, volume);
	}
	
	public void setPitch(float pitch) {
		alSourcef(source, AL_PITCH, pitch);
	}
	
	public void destroy() {
		alDeleteSources(source);
	}
	
}

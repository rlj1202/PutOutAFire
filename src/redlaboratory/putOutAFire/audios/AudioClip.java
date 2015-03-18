package redlaboratory.putOutAFire.audios;

import static org.lwjgl.openal.AL10.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class AudioClip {
	
	private int source;
	private FloatBuffer position;
	private FloatBuffer velocity;
	private FloatBuffer orientation;
	
	public AudioClip(int buffer, float x, float y, float z, float volume, float pitch, boolean loop) {
		position = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] {x, y, z}).rewind();
		velocity = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] {0, 0, 0}).rewind();
		orientation = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[] {0, 0, 0, 0, 0, 0}).rewind();
		
		source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
		alSourcef(source, AL_GAIN, volume);
		alSourcef(source, AL_PITCH, pitch);
		
		alSource(source, AL_POSITION, position);
		alSource(source, AL_VELOCITY, velocity);
		alSource(source, AL_ORIENTATION, orientation);
		
		alSourcei(source, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
	}
	
	public void play() {
		alSourcePlay(source);
	}
	
	public void pause() {
		alSourcePause(source);
	}
	
	public void stop() {
		alSourceStop(source);
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
	
	public void setLoop(boolean loop) {
		alSourcei(source, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
	}
	
}

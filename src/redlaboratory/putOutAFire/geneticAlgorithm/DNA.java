package redlaboratory.putOutAFire.geneticAlgorithm;

import java.util.Arrays;

public class DNA implements Cloneable {
	
	public final int length;
	public final byte[] baseArray;
	
	public DNA(int length) {
		this.length = length;
		this.baseArray = new byte[length];
	}
	
	public DNA(byte[] baseArray) {
		this.length = baseArray.length;
		this.baseArray = baseArray;
	}
	
	@Override
	public DNA clone() {
		return new DNA(Arrays.copyOf(baseArray, length));
	}
	
	@Override
	public String toString() {
		return Arrays.toString(baseArray);
	}
	
}

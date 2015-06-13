package redlaboratory.putOutAFire.geneticAlgorithm;

import java.util.Arrays;

public class DNAManager {
	
	public static DNA[] makeChildren(int amount, DNA... parents) {
		DNA[] children = new DNA[amount];
		
		for (int i = 0; i < amount; i++) {
			int rndIndexA = (int) (Math.random() * parents.length);
			int rndIndexB = (int) (Math.random() * parents.length);
			
			children[i] = makeChildren(1, parents[rndIndexA], parents[rndIndexB])[0];
		}
		
		return children;
	}
	
	private static DNA[] makeChildren(int amount, DNA a, DNA b) {
		DNA[] children = new DNA[amount];
		
		for (int i = 0; i < amount; i++) {
			int rndInt = (int) (Math.random() * 2);
			
			if (rndInt == 0) {
				DNA childA = a.clone();
				DNA childB = b.clone();
				
				int j = 0;
				do {
					crossover(childA, childB);
					
					j++;
				} while (j < (int) (Math.random() * 3));
				
				children[i] = (Math.random() < 0.5) ? (childA) : (childB);
			} else if (rndInt == 1) {
				DNA child = (Math.random() < 0.5) ? (a.clone()) : (b.clone());
				
				int j = 0;
				do {
					mutation(child);
					
					j++;
				} while (j < (int) (Math.random() * a.length / 2));
				
				children[i] = child;
			}
		}
		
		return children;
	}
	
	public static void mutation(DNA dna) {
		int rndPos = (int) (Math.random() * dna.length);
		byte rndBase = (byte) (Math.random() * Byte.MAX_VALUE);
		
		if (Math.random() < 0.5) dna.baseArray[rndPos] = rndBase;
		else insert(dna.baseArray, rndPos, new byte[] {rndBase});
	}

	public static void crossover(DNA dnaA, DNA dnaB) {
		if (dnaA.length == dnaB.length) {
			int from = (int) (Math.random() * (dnaA.length + 1));
			int to = (int) (Math.random() * (dnaA.length + 1));
			
			if (from > to) {
				int tmp = from;
				
				from = to;
				to = tmp;
			} else if (from == to) {
				return;
			}
			
			byte[] a = Arrays.copyOfRange(dnaA.baseArray, from, to);
			byte[] b = Arrays.copyOfRange(dnaB.baseArray, from, to);
			
			replace(dnaA.baseArray, from, b);
			replace(dnaB.baseArray, from, a);
		}
	}
	
	public static void replace(byte[] original, int startIndex, byte[] source) {
		for (int i = 0; i < source.length; i++) {
			if (startIndex + i >= original.length) return;
			
			original[startIndex + i] = source[i];
		}
	}
	
	public static void insert(byte[] original, int pos, byte[] source) {
		byte[] tmp = Arrays.copyOfRange(original, pos, original.length);
		
		replace(original, pos, source);
		replace(original, pos + source.length, tmp);
	}
	
}

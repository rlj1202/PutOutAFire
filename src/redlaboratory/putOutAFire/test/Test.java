package redlaboratory.putOutAFire.test;

import java.util.Arrays;

import redlaboratory.putOutAFire.geneticAlgorithm.*;
import redlaboratory.putOutAFire.neuralNetwork.*;

public class Test {

	public static void main(String[] args) {
		test_2();
	}
	
	public static void test_1() {
		DNA a = new DNA(new byte[] {0, 1, 2, 3, 4});
		DNA b = new DNA(new byte[] {5, 6, 7, 8, 9});
		
		DNA[] children = DNAManager.makeChildren(10, a, b);
		
		System.out.println(a.toString());
		System.out.println(b.toString());
		
		System.out.println();
		
		for (DNA child : children) {
			System.out.println(child.toString());
		}
	}
	
	public static void test_2() {
		NeuralNetwork network = new NeuralNetwork(new int[] {1, 2, 1}, new float[] {1.0f, 1.0f, 0.5f, 0.5f});
		
		network.getInputNeurons()[0].setInputValue(5);
		
		System.out.println(Arrays.toString(network.getOutputValues()));
	}
	
}

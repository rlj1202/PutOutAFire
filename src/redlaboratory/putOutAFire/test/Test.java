package redlaboratory.putOutAFire.test;

import redlaboratory.putOutAFire.geneticAlgorithm.DNA;
import redlaboratory.putOutAFire.geneticAlgorithm.DNAManager;

public class Test {

	public static void main(String[] args) {
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
	
}

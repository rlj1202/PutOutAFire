package redlaboratory.putOutAFire.neuralNetwork;

import java.util.ArrayList;

public class Neuron extends AbstractNeuron {
	
	private ArrayList<AbstractNeuron> inputNeurons;
	
	public Neuron(double weight) {
		super(weight);
	}
	
	public double getOutputValue() {
		return transfer(summation(inputNeurons)) * weight;
	}
	
	private static double summation(ArrayList<AbstractNeuron> inputNeurons) {
		double result = 0;
		
		for (AbstractNeuron neuron : inputNeurons) {
			result += neuron.getOutputValue();
		}
		
		return result;
	}
	
	private static double transfer(double value) {
		return value;
	}
	
}

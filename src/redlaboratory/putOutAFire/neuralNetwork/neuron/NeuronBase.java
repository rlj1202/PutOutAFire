package redlaboratory.putOutAFire.neuralNetwork.neuron;

public class NeuronBase implements Neuron {
	
	private int inputNeuronsAmount;
	
	private Neuron[] inputNeurons;
	private float[] weights;
	
	public NeuronBase(Neuron[] inputNeurons, float[] weights) {
		this.inputNeuronsAmount = inputNeurons.length;
		
		this.inputNeurons = inputNeurons;
		this.weights = weights;
	}
	
	public float getOutputValue(ActivationFunction function) {
		float sum = 0;
		
		for (int i = 0; i < inputNeuronsAmount; i++) {
			Neuron neuron = inputNeurons[i];
			float weight = weights[i];
			
			sum += neuron.getOutputValue(function) * weight;
		}
		
		return function.active(sum);
	}
	
}

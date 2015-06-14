package redlaboratory.putOutAFire.neuralNetwork.neuron;

public class NeuronInput implements Neuron {
	
	private float inputValue;
	
	public NeuronInput() {
		this(0);
	}
	
	public NeuronInput(float inputValue) {
		this.inputValue = inputValue;
	}
	
	public void setInputValue(float value) {
		inputValue = value;
	}
	
	public float getOutputValue(ActivationFunction function) {
		return inputValue;
	}
	
}

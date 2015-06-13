package redlaboratory.putOutAFire.neuralNetwork;

public class InputNeuron extends AbstractNeuron {
	
	private double inputValue;
	
	public InputNeuron(double weight) {
		this(weight, 0);
	}
	
	public InputNeuron(double weight, double inputValue) {
		super(weight);
		
		this.inputValue = inputValue;
	}
	
	public void setInputValue(double value) {
		inputValue = value;
	}
	
	public double getInputValue() {
		return inputValue;
	}
	
	public double getOutputValue() {
		return inputValue * weight;
	}
	
}

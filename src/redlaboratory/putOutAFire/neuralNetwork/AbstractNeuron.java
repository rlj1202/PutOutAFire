package redlaboratory.putOutAFire.neuralNetwork;

abstract public class AbstractNeuron {
	
	protected double weight;
	
	public AbstractNeuron(double weight) {
		this.weight = weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	abstract public double getOutputValue();
	
}

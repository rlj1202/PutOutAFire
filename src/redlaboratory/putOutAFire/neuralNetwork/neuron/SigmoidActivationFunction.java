package redlaboratory.putOutAFire.neuralNetwork.neuron;

public class SigmoidActivationFunction implements ActivationFunction {

	@Override
	public float active(float x) {
		return (float) (1.0d / (1.0d + Math.exp(-x)));
	}

}

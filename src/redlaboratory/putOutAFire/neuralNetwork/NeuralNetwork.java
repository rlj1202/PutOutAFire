package redlaboratory.putOutAFire.neuralNetwork;

import java.util.Arrays;

import redlaboratory.putOutAFire.neuralNetwork.neuron.NeuronInput;
import redlaboratory.putOutAFire.neuralNetwork.neuron.Neuron;
import redlaboratory.putOutAFire.neuralNetwork.neuron.NeuronBase;
import redlaboratory.putOutAFire.neuralNetwork.neuron.SigmoidActivationFunction;

public class NeuralNetwork {
	
	public int[] neuronAmounts;
	public float[] weights;
	
	private Neuron[] neurons;
	
	private NeuronInput[] inputNeurons;
	private Neuron[] outputNeurons;
	
	public NeuralNetwork(int[] neuronAmounts, float[] weights) {
		this.neuronAmounts = neuronAmounts;
		this.weights = weights;
		
		int totalNeuronAmount = 0;
		for (int i : neuronAmounts) totalNeuronAmount += i;
		neurons = new Neuron[totalNeuronAmount];
		
		//
		
		int neuronIndex = 0;
		int weightPos = 0;
		
		Neuron[] previousLayer = null;
		Neuron[] currentLayer = null;
		
		{
			int neuronAmount = neuronAmounts[0];
			
			previousLayer = new NeuronInput[neuronAmount];
			
			for (int i = 0; i < neuronAmount; i++) {
				NeuronInput neuron = new NeuronInput();
				previousLayer[i] = neuron;
				
				neurons[neuronIndex] = neuron;
				neuronIndex++;
			}
			
			inputNeurons = (NeuronInput[]) previousLayer;
		}
		
		{
			for (int layer = 1; layer < neuronAmounts.length; layer++) {
				int neuronAmount = neuronAmounts[layer];
				
				currentLayer = new Neuron[neuronAmount];
				
				for (int i = 0; i < neuronAmount; i++) {
					NeuronBase neuron = new NeuronBase(previousLayer, Arrays.copyOfRange(weights, weightPos, weightPos + previousLayer.length));
					currentLayer[i] = neuron;
					
					neurons[neuronIndex] = neuron;
					neuronIndex++;
					
					weightPos += previousLayer.length;
				}
				
				previousLayer = currentLayer;
			}
			
			outputNeurons = previousLayer;
		}
	}
	
	public NeuronInput[] getInputNeurons() {
		return inputNeurons;
	}
	
	public Neuron[] getOutputNeurons() {
		return outputNeurons;
	}
	
	public void setInputValues(float[] values) {
		for (int i = 0; i < values.length; i++) {
			inputNeurons[i].setInputValue(values[i]);
		}
	}
	
	public float[] getOutputValues() {
		float[] values = new float[outputNeurons.length];
		
		for (int i = 0; i < outputNeurons.length; i++) {
			values[i] = outputNeurons[i].getOutputValue(new SigmoidActivationFunction());
		}
		
		return values;
	}
	
	public static int getWeightAmount(int[] neuronAmounts) {
		int amount = 0;
		
		for (int i = 0; i < neuronAmounts.length - 1; i++) {
			amount += neuronAmounts[i] * neuronAmounts[i + 1];
		}
		
		return amount;
	}
	
}

package neural_network;

import java.util.Random;

public class Neuron {
	double[] weights;
	Random random = new Random();

	Neuron(int n) {
		weights = new double[n];	//about each inputs
		for (int i=0; i<weights.length; i++) {
			weights[i] = random.nextDouble();	//random values of weights -1 ~ 1
		}	
	}
	
}

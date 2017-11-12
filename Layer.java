package neural_network;

public class Layer {
	Perceptron[] ptrons;
	double[] outputs;
	
	Layer(double[] input, int desired, int ptron_num) {
		for(int i=0; i<ptron_num; i++) {
			ptrons[i] = new Perceptron(input, desired);
			outputs[i] = ptrons[i].feedforward();
		}
	}

}

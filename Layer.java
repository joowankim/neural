package neural_network;

public class Layer {
	Perceptron[] ptrons;
	int ptrons_num;
	double[] outputs;
	double[] inputs;
	
	Layer(int n, int ptron_num) {
		ptrons_num	= ptron_num;
		ptrons = new Perceptron[ptrons_num];
		outputs = new double[ptrons_num];
		
		for(int i=0; i<ptrons_num; i++) {
			ptrons[i] = new Perceptron(n);
		}
	}
	
	void computeOutput() {
		for (int i=0; i<ptrons_num; i++) {
			outputs[i] = ptrons[i].feedforward(inputs);
		}
	}

}

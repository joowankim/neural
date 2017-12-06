package neural_network;

public class Layer {
	Perceptron[] ptrons;
	int ptrons_num;
	double[] outputs;
	double[] inputs;

	Layer(int n, int ptron_num) {
		ptrons_num = ptron_num;
		ptrons = new Perceptron[ptrons_num];
		outputs = new double[ptrons_num];

		for (int i = 0; i < ptrons_num; i++) {
			ptrons[i] = new Perceptron(n);
		}
	}

	void computeOutput() {
		int i = 0;
		for (i = 0; i < ptrons_num; i++) {
			outputs[i] = activate(ptrons[i].feedforward(inputs));
		}
		double m = max();
		double s = sum(m);
		for (i = 0; i < ptrons_num; i++) {
			outputs[i] = Math.exp(outputs[i] - m)/s;
		}
	}

	double max() {
		double max = outputs[0];
		for (int i = 0; i < outputs.length; i++) {
			if (outputs[i] > max)
				max = outputs[i];
		}
		return max;
	}
	
	double sum(double m) {
		double s = 0;
		for (int i = 0; i<outputs.length; i++)
			s += Math.exp(outputs[i] - m);
		return s;
	}

	double sigmoid(double z) {
		double r;

		if (z < -10)
			r = 1 / (1 + Math.exp(10));
		else if (z > 10)
			r = 1 / (1 + Math.exp(-10));
		else
			r = 1 / (1 + Math.exp(-z));

		return r;
	}

	double activate(double z) {
		// if (z > 0) return 1;
		// else return 0;
		//return sigmoid(z);
		return z;
	}

}

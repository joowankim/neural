package neural_network;

import java.util.Random;

public class Perceptron {
	double[] weights;
	Random random = new Random();
	
	Perceptron(int n) {
		weights = new double[n];

		for(int i=0; i<n; i++) {
			weights[i] = random.nextDouble();	//random values of weights -1 ~ 1
		}
	}
	
	int feedforward(double[] inputs) {
		
		double sum = 0;
		
		for (int i = 0; i < weights.length; i++) {
			sum += inputs[i]*weights[i];	//������ ������ (n�����϶� n-1������ ������) input�� n��
		}

		return activate(sum);
	}
	
	int activate(double sum) {
		if (sum > 0) return 1;
		else return -1;
	}
	
}
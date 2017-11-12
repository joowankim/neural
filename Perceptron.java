package neural_network;

import java.util.Random;

public class Perceptron {
	double[] inputs;
	double[] weights;
	Random random = new Random();
	
	Perceptron(double[] input) {
		int n = input.length + 1;
		weights = new double[n];
		inputs = new double[n];
		
		for(int i=0; i<n; i++) {
			if(i==0) {
				inputs[i] = 1;	//bias
			}
			else {
				inputs[i] = input[i-1];
			}
			weights[i] = random.nextDouble();	//random values of weights -1 ~ 1
		}
	}
	
	int feedforward() {
		double sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += inputs[i]*weights[i];	//직선의 방정식 (n차원일때 n-1차원의 방정식) input이 n개
		}

		return activate(sum);
	}
	
	int activate(double sum) {
		if (sum > 0) return 1;
		else return -1;
	}
	
}

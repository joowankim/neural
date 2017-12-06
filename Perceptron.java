package neural_network;

import java.util.Random;

public class Perceptron {
	double[] weights;
	Random random = new Random();

	Perceptron(int n) {
		weights = new double[n + 1];

		for (int i = 0; i < n + 1; i++) {
			weights[i] = random.nextDouble(); // random values of weights -1 ~ 1
		}
	}

	double feedforward(double[] inputs) {

		double sum = 0;

		for (int i = 0; i < weights.length; i++) {
			sum += inputs[i] * weights[i]; // 직선의 방정식 (n차원일때 n-1차원의 방정식) input이 n개
		}
		return sum;
	}

}

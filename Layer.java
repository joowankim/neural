package ANN;

public class Layer {

	// delta == 0
	boolean breakPoint = false;
	
	// initialized
	int row; // #output
	int col; // #input
	double[][] weight;

	// for forward
	Function activate;

	// for backward
	double[] out;
	double[] input;
	double learningRate = 0.0007;

	Layer(int inputNum, int outputNum, Function activation) {

		col = inputNum + 1;
		row = outputNum;
		activate = activation;
		weight = new double[row][col];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				weight[i][j] = Math.random() * 2 - 1; // -1 ~ 1
			}
		}
	}

	double[] addBias(double[] inputs) {

		double[] plusOne = new double[inputs.length + 1];
		int i;

		for (i = 0; i < inputs.length; i++) {
			plusOne[i] = inputs[i];
		}
		plusOne[i] = 1; // add bias

		return plusOne;
	}

	double[] feedforward(double[] inputs) {

		input = addBias(inputs);

		double[] z = new double[row]; 	// sum(weight*input) before activate
										
		for (int i = 0; i < row; i++) {
			z[i] = 0;
			for (int j = 0; j < col; j++) {
				z[i] += weight[i][j] * input[j];
			}
		}

		out = activate.calc(z); // activating output
	
		return out;
	}

	double[] renewWeight(double[] err) {

		int i, j;
		double[] delta = new double[err.length];
		for (i = 0; i < err.length; i++) {
			delta[i] = err[i] * activate.calcDeff(out[i]);
		}

		double[] deltaSum = new double[col-1];	// -1은 bias 개수 빼주는것
		for (i = 0; i < col-1; i++) {
			deltaSum[i] = 0;
			for (j = 0; j < row; j++) {
				deltaSum[i] += weight[j][i] * delta[j];
			}
		}

		for (i = 0; i < row; i++) {
			for (j = 0; j < col; j++) {
				weight[i][j] += learningRate * delta[i] * input[j];
			}
		}
		

		return deltaSum;
	}
	
}

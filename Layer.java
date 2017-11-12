package neural_network;

public class Layer {
	Perceptron[] ptrons;
	int ptrons_num;
	double[] outputs;
	double learningRate = 0.01;
	
	Layer(double[] input, int ptron_num) {
		ptrons_num	= ptron_num;
		ptrons = new Perceptron[ptrons_num];
		outputs = new double[ptrons_num];
		
		for(int i=0; i<ptrons_num; i++) {
			ptrons[i] = new Perceptron(input);
			outputs[i] = ptrons[i].feedforward();
		}
	}

	void adjustWeight(double desired, double guess) {
		double error = desired - guess;

		for (int i = 0; i < ptrons_num; i++) {
			for(int j = 0; j <ptrons[i].weights.length; j++)
			ptrons[i].weights[j] += learningRate * error * ptrons[i].inputs[j];
		}	/* 잘못된 input이 들어와서 최종 weight를 틀리게 바꿀 수 도 있으므로 해당 input에 완전히 맞도록 weight 바꾸진 않는다
		 	 * input의 오류를 무시할 수 있도록 weight 변화 횟수를 제한하고 learning rate(c)를 적절한 값으로 지정하는게 point
		 	 */
	}
}

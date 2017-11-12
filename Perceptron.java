package neural_network;

public class Perceptron {
	double[] inputs;
	int answer;
	double learningRate = 0.01;
	Neuron nron;
	
	Perceptron(double[] input, int desired) {
		int n = input.length + 1;
		inputs = new double[n];
		nron = new Neuron(n);
		
		for(int i=0; i<n; i++) {
			if(i==0) {
				answer = desired;
				inputs[i] = 1;
			}
			else {
				inputs[i] = input[i];
			}
		}
	}
	
	int feedforward() {
		double sum = 0;
		for (int i = 0; i < nron.weights.length; i++) {
			sum += inputs[i]*nron.weights[i];	//직선의 방정식
		}
		System.out.println("sum : " + sum);
		return activate(sum);
	}
	
	int activate(double sum) {
		if (sum > 0) return 1;
		else return -1;
	}
	
	void train(int desired) {
		int guess = feedforward();
		double error = desired - guess;
		System.out.println("guess :" + guess + " error : " + error);
		for (int i = 0; i < nron.weights.length; i++) {
			nron.weights[i] += learningRate * error * inputs[i];
		}	/* 잘못된 input이 들어와서 최종 weight를 틀리게 바꿀 수 도 있으므로 해당 input에 완전히 맞도록 weight 바꾸진 않는다
		 	 * input의 오류를 무시할 수 있도록 weight 변화 횟수를 제한하고 learning rate(c)를 적절한 값으로 지정하는게 point
		 	 */
		System.out.println("2nd : " + feedforward());
	}
}

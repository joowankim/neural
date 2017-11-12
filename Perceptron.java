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
			sum += inputs[i]*nron.weights[i];	//������ ������
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
		}	/* �߸��� input�� ���ͼ� ���� weight�� Ʋ���� �ٲ� �� �� �����Ƿ� �ش� input�� ������ �µ��� weight �ٲ��� �ʴ´�
		 	 * input�� ������ ������ �� �ֵ��� weight ��ȭ Ƚ���� �����ϰ� learning rate(c)�� ������ ������ �����ϴ°� point
		 	 */
		System.out.println("2nd : " + feedforward());
	}
}

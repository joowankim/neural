package ai;

public class Layer {
	// �ԷµǴ� �� ����
	int inputCount;
	// ��µǴ� �� ����
	int outputCount;

	// Ȱ���Լ�
	Function activation;

	// ����ġ
	double[][] weight;
	// Back propagation�� �̿��� ����°� ����� ����
	double[] input;
	double[] output;
	static double[] answer;

	// �н��ӵ�
	double learningRate;

	/**
	 * 
	 * @param input
	 *            �ԷµǴ� �� ����
	 * @param output
	 *            ����� �� ����(=���̾��� ��� ����)
	 * @param activation
	 *            Ȱ���Լ�
	 */
	public Layer(int input, int output, Function activation) {
		this.inputCount = input;
		this.outputCount = output;
		this.weight = new double[output][input];
		this.activation = activation;
	}

	/**
	 * ������ ������ weight�ʱ�ȭ
	 * 
	 * @param min
	 * @param max
	 */
	public void randomize(double min, double max) {
		for (int i = 0; i < outputCount; i++) {
			for (int j = 0; j < inputCount; j++) {
				weight[i][j] = (max - min) * Math.random() + min;
			}
		}
	}

	/**
	 * ������ ���
	 * 
	 * @param input
	 * @return
	 */
	public double[] calc(double[] input, double[] answer) {
		this.input = new double[this.inputCount];
		this.answer = answer;

		int k;
		for (k = 0; k < input.length; k++)
			this.input[k] = input[k];
		this.input[k] = 1.0; // bias �߰�

		double[] output = new double[this.outputCount];
		for (int i = 0; i < this.outputCount; i++) {
			output[i] = 0;
			for (int j = 0; j < this.inputCount; j++) {
				output[i] += this.input[j] * weight[i][j];
			}
		}

		return (this.output = activation.calc(output));
	}

	/**
	 * Back propagation
	 * 
	 * @param err
	 *            �Էµ� ����.
	 * @return �ٷ� ���� ���̾��� ����.
	 */
	public double[] backProp(double[] err) {
		double[] delta = new double[outputCount];
		for (int i = 0; i < delta.length; i++) {
			delta[i] = activation.calcDeff(output[i]) * err[i];
		}

		double[] output = new double[inputCount];
		for (int i = 0; i < inputCount; i++) {
			output[i] = 0;
			for (int j = 0; j < outputCount; j++) {
				output[i] += delta[j] * weight[j][i];
			}
		}

		for (int i = 0; i < outputCount; i++) {
			for (int j = 0; j < inputCount; j++) {
				weight[i][j] += learningRate * delta[i] * input[j];
			}
		}
		return output;
	}

	public static Function SOFTMAX = new Function() {

		int i;
		double sum = 0;

		@Override
		public double[] calc(double[] input) {
			double[] r = new double[input.length];
			int max = 0;
			for (i = 0; i < input.length; i++) {
				if (input[i] > input[max])
					max = i;
			}
			for (i = 0; i < input.length; i++)
				sum += Math.exp(input[i] - input[max]);

			for (i = 0; i < input.length; i++)
				r[i] = calc(input[i] - input[max]);

			return r;
		}

		@Override
		public double[] calcDeff(double[] input) {
			double[] r = new double[input.length];
			for (i = 0; i < input.length; i++)
				r[i] = calc(input[i]) - answer[i];

			return r;
		}

		@Override
		protected double calc(double input) {
			return Math.exp(input) / sum;
		}

		@Override
		protected double calcDeff(double input) {
			return calc(input) - 1;
		}
	};

}

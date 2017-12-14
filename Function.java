package ANN;

public abstract class Function {

	/**
	 * �Լ� ��ü. �Լ� ����� �����ϰ� �ϱ� ���� ����Ѵ�. �̰��� ����Ͽ� �Լ� ��ü�� ����� �ȴ�.
	 * 
	 * 
	 */
	
	public static Function SIGMOID = new Function() {

		@Override
		protected double calc(double input) {
			return 1.0 / (Math.exp(-input) + 1);
		}

		@Override
		protected double calcDeff(double input) {
			return calc(input) * (1 - calc(input));
		}

	};

	public static Function RELU = new Function() {

		@Override
		protected double calc(double input) {
			return Math.max(input, 0);
		}

		@Override
		protected double calcDeff(double input) {
			if (input > 0) {
				return 1;
			}
			return 0;
		}

	};
	
	public static Function STEP = new Function() {
		
		@Override
		protected double calc(double input) {
			if(input > 0) return 1;
			else return 0;
		}
		
		@Override
		protected double calcDeff(double input) {
			return 1;
		}
	};

	/**
	 * � �Լ�.
	 * 
	 * @param input
	 * @return
	 */
	public double[] calc(double[] input) {
		
		double[] inputs = new double[input.length];
		
		for (int i = 0; i < input.length; i++) {
			inputs[i] = calc(input[i]);
		}
		return inputs;
	}

	/**
	 * ������ �Լ��� ���Լ�
	 * 
	 * @param input
	 * @return
	 */
	public double[] calcDeff(double[] input) {
		
		double[] inputs = new double[input.length];
		
		for (int i = 0; i < input.length; i++) {
			inputs[i] = calcDeff(input[i]);
		}
		return inputs;
	}

	// �� �Ʒ��� �� �Լ���, ������ �� ���ҿ� ���� ������ �� ���� ���Ѵ�.
	protected abstract double calc(double input);

	protected abstract double calcDeff(double input);
}
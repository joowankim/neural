package ai;

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

	public static Function ATAN = new Function() {

		@Override
		protected double calc(double input) {
			return 1.0 / (input * input + 1);
		}
		
		@Override
		protected double calcDeff(double input) {
			return Math.atan(input);
		}
	};
	
	

	/**
	 * � �Լ�.
	 * 
	 * @param input
	 * @return
	 */
	public double[] calc(double[] input) {
		
		for (int i = 0; i < input.length; i++) {
			input[i] = calc(input[i]);
		}
		return input;
	}

	/**
	 * ������ �Լ��� ���Լ�
	 * 
	 * @param input
	 * @return
	 */
	public double[] calcDeff(double[] input) {
		for (int i = 0; i < input.length; i++) {
			input[i] = calcDeff(input[i]);
		}
		return input;
	}

	// �� �Ʒ��� �� �Լ���, ������ �� ���ҿ� ���� ������ �� ���� ���Ѵ�.
	protected abstract double calc(double input);

	protected abstract double calcDeff(double input);
}
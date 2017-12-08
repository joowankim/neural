package ai;

public abstract class Function {

	/**
	 * 함수 객체. 함수 계산을 용이하게 하기 위해 사용한다. 이것을 상속하여 함수 객체를 만들면 된다.
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
	 * 어떤 함수.
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
	 * 구현할 함수의 도함수
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

	// 이 아래의 두 함수는, 벡터의 각 원소에 무슨 연산을 할 지를 정한다.
	protected abstract double calc(double input);

	protected abstract double calcDeff(double input);
}
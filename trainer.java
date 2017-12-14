package ANN;

public class trainer {

	allLayer learner;
	double[][] dataSet;
	double[][] answerSet;
	Painting p;
	
	double[] guess;
	int breakP = 0;

	trainer(allLayer Learner, double[][] data, double[][] answer) {

		learner = Learner;
		dataSet = data;
		answerSet = answer;
		p = new Painting(Learner);
	}

	double[] learning(int learningCount) {

		if (dataSet.length == answerSet.length) {
			double[] error = new double[dataSet.length * learningCount];
			int k = 0;

			for (int i = 0; i < learningCount; i++) {
				for (int j = 0; j < dataSet.length; j++) {
					double[] output = learner.forwarding(dataSet[j]);
					double[] err = getErr(output, answerSet[j]);
					error[k] = errorAbs(err);
					learner.backProp(err);
					p.swing();
					k++;
				
				}
			}
			return error;
		} else {
			System.out.println("ERROR");
			return null;
		}
	}

	double[] test() {
		//int cnt = 0;
		if (dataSet.length == answerSet.length) {
			double[] error = new double[dataSet.length];
			guess = new double[dataSet.length];
			int k = 0;
			for (int j = 0; j < dataSet.length; j++) {
				double[] output = learner.forwarding(dataSet[j]);
				double[] err = getErr(output, answerSet[j]);
				error[k] = errorAbs(err);	
				k++;
			
				if(output[0] > output[1]) guess[j] = 0.01;
				else guess[j] = 0.99;
				
				//System.out.println(output[0] + " : " + output[1]);
				
				
			}
			return error;
		} else {
			System.out.println("ERROR");
			return null;
		}

	}

	double[] getErr(double[] O, double[] D) {
		double[] err = new double[O.length];
		for (int i = 0; i < O.length; i++)
			err[i] = D[i] - O[i];
		return err;
	}

	double errorAbs(double[] err) {
		double error = 0;
		for (double e : err)
			error += e * e;
		return Math.sqrt(error);
	}
}

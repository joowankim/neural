package neural_network;

public class HiddenLayer {
	Layer[] layer;
	int numOfLayer;
	double learningRate = 0.01;
	double guess;
	
	HiddenLayer(int n, int[] ptron_num, int order) {
		int i;
		numOfLayer = order;
		layer = new Layer[numOfLayer];
		for(i=0; i<order; i++) {
			if(i==0) {
				layer[i] = new Layer(n, ptron_num[i]);
			}
			else {
				layer[i] = new Layer(ptron_num[i-1], ptron_num[i]);
			}
		}
		//layer[i] = new Layer(ptron_num[i-1], 1);	//only one output
	}
	
	void forwardInput(double[] input) {
		int i,j;
		for(i=0; i<numOfLayer; i++) {
			
			if (i==0) {
				System.out.println("input : " + input.length);
				layer[i].inputs = new double[input.length+1];
				for(j=0; j<input.length; j++) layer[i].inputs[j] = input[j];
				layer[i].inputs[j] = 1;	//bias
			}
			else {
				System.out.println("layer[" + i + "] : " + layer[i-1].outputs.length);
				layer[i].inputs = new double[layer[i-1].outputs.length+1];
				for(j=0; j<input.length; j++) layer[i].inputs[j] = layer[i-1].outputs[j];
				layer[i].inputs[j] = 1;	//bias
			}
			layer[i].computeOutput();
		}
		guess = layer[i-1].outputs[0];
		System.out.println("end");
	}
	
	void adjustWeight(double desired) {
		double error = desired - guess;
		
		for(int k=0; k<numOfLayer; k++) {
			//error = error*(1-guess)*guess;
			for(int i = 0; i < layer[k].ptrons_num; i++) {
				for(int j = 0; j <layer[k].ptrons[i].weights.length; j++)
					layer[k].ptrons[i].weights[j] += learningRate * error * layer[k].inputs[j];
			}	/* 잘못된 input이 들어와서 최종 weight를 틀리게 바꿀 수 도 있으므로 해당 input에 완전히 맞도록 weight 바꾸진 않는다
			 	* input의 오류를 무시할 수 있도록 weight 변화 횟수를 제한하고 learning rate(c)를 적절한 값으로 지정하는게 point
			 	*/
		}
	}

}

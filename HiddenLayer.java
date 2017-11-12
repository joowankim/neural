package neural_network;

public class HiddenLayer {
	Layer[] layer;
	int numOfLayer;
	double learningRate = 0.01;
	double guess;
	
	HiddenLayer(int n, int[] ptron_num, int order) {
		int i;
		numOfLayer = order + 1;
		layer = new Layer[numOfLayer];
		for(i=0; i<order; i++) {
			if(i==0) {
				layer[i] = new Layer(n, ptron_num[i]);
			}
			else {
				layer[i] = new Layer(ptron_num[i-1], ptron_num[i]);
			}
		}
		layer[i] = new Layer(ptron_num[i-1], 1);	//only one output
	}
	
	void forwardInput(double[] input) {
		int i;
		for(i=0; i<numOfLayer; i++) {
			if (i==0) {
				layer[i].inputs = input;
			}
			else {
				layer[i].inputs = layer[i-1].outputs;
			}
		}
		guess = layer[i-1].outputs[0];
	}
	
	void adjustWeight(double desired) {
		double error = desired - guess;
		
		for(int k=0; k<numOfLayer; k++) {
			for(int i = 0; i < layer[k].ptrons_num; i++) {
				for(int j = 0; j <layer[k].ptrons[i].weights.length; j++)
					layer[k].ptrons[i].weights[j] += learningRate * error * layer[k].inputs[j];
			}	/* �߸��� input�� ���ͼ� ���� weight�� Ʋ���� �ٲ� �� �� �����Ƿ� �ش� input�� ������ �µ��� weight �ٲ��� �ʴ´�
			 	* input�� ������ ������ �� �ֵ��� weight ��ȭ Ƚ���� �����ϰ� learning rate(c)�� ������ ������ �����ϴ°� point
			 	*/
		}
	}

}

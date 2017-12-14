package ANN;

public class allLayer {

	// initialized
	Layer[] layers;
	int layerNum;
	int lastLayerIdx;

	// result of forward
	double[] output;

	allLayer(int[] nodeNum, Function activate) {

		layerNum = nodeNum.length - 1; // except for output layer
		lastLayerIdx = layerNum - 1;
		layers = new Layer[nodeNum.length];
		int i = 1;
		layers[0] = new Layer(nodeNum[0], nodeNum[1], activate);
		for (i = 1; i < layerNum-1; i++) {
			layers[i] = new Layer(nodeNum[i], nodeNum[i + 1], activate);
		}layers[i] = new Layer(nodeNum[i], nodeNum[i + 1], Function.SIGMOID);
	}

	double[] forwarding(double[] inputs) {

		layers[0].feedforward(inputs);

		for (int i = 1; i < layerNum; i++) {
			layers[i].feedforward(layers[i - 1].out);
		}
		output = layers[lastLayerIdx].out;

		return output;
		
	}

	void backProp(double[] err) {

		double[] error = layers[lastLayerIdx].renewWeight(err);
		for (int i = lastLayerIdx - 1; i >= 0; i--) {
			error = layers[i].renewWeight(error);
		}

		//printWeight();
	}

	void printWeight() {
		for (int k = 0; k < layerNum; k++) {
			System.out.println("layer " + k);
			for (int i = 0; i < layers[k].row; i++) {
				for (int j = 0; j < layers[k].col; j++) {
					System.out.print(layers[k].weight[i][j] + " ");
				}
				System.out.println(" ");
			}
			System.out.println("");
		}
	}

}

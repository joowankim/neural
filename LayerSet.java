package ai;

public class LayerSet {

	Function activation;
	Layer[] layerSet;
	int size;

	/**
	 * 레이어셋 하나가 신경망 하나에 해당한다.
	 * 
	 * @param nodeCount
	 *            레이어당 노드의 개수. 처음 값은 입력값의 개수다. 예를 들어 2,3,3,4라고 하면, 입력이 2개이고, 3개의 노드가
	 *            있는 히든 레이어가 두 개, 4개의 노드를 가지는 출력 레이어가 있는 신경망을 만든다.
	 * @param activation
	 */
	public LayerSet(int[] nodeCount, Function activation) {
		int i;
		this.size = nodeCount.length - 1;
		this.activation = activation;
		layerSet = new Layer[size];
		layerSet[0] = new Layer(nodeCount[0] + 1, nodeCount[1], activation);
		for (i = 1; i < size - 1; i++) {
			layerSet[i] = new Layer(nodeCount[i] + 1, nodeCount[i + 1], activation);
		}
		layerSet[i] = new Layer(nodeCount[i] + 1, nodeCount[i + 1], layerSet[i].SOFTMAX);
	}

	// Randomizer
	public void randomize(double min, double max) {
		for (Layer layer : layerSet) {
			layer.randomize(min, max);
		}
	}

	/**
	 * 순방향 계산. 레이어를 계산 - 계산값을 변수에 저장 - 다음 레이어에 변수 값 대입 - 계산값을 변수에 저장...을 반복하여 인공신경망을
	 * 구현한다.
	 * 
	 * @param input
	 * @return
	 */
	public double[] calc(double[] input, double[] answer) {
		double[] datas = layerSet[0].calc(input, answer);
		for (int i = 1; i < layerSet.length; i++) {
			datas = layerSet[i].calc(datas, answer);
		}
		return datas;
	}

	/**
	 * 순방향 계산과 같은 방법으로 오류를 변수에 저장 - 레이어 역전파 - 다음 레이어 오류를 변수에 저장 - 다음 레이어 역전파에 변수 값
	 * 대입...을 반복하여 역전파를 구현한다.
	 * 
	 * @param err
	 * @return
	 */
	public double[] backProp(double[] err, double[] answer) {
		double[] error = layerSet[size - 1].backProp(answer);	// softmax
		for (int i = size - 2; i >= 0; i--) {
			error = layerSet[i].backProp(error);
		}
		return error;
	}

	// Set learning rate
	public void setLearningRate(double learningRate) {
		for (Layer i : layerSet) {
			i.learningRate = learningRate;
		}
	}
	
	public void printWeight() {
		for(Layer ls : layerSet) {
			for(double[] w1 : ls.weight) {
				for(int i=0; i<w1.length; i++) {
					System.out.print(w1[i] + " ");
				}
				System.out.println("");
			}
			System.out.println("layer end");
		}
		System.out.println("next data set");
	}
}

package ai;

public class LayerSet {

	Function activation;
	Layer[] layerSet;
	int size;

	/**
	 * ���̾�� �ϳ��� �Ű�� �ϳ��� �ش��Ѵ�.
	 * 
	 * @param nodeCount
	 *            ���̾�� ����� ����. ó�� ���� �Է°��� ������. ���� ��� 2,3,3,4��� �ϸ�, �Է��� 2���̰�, 3���� ��尡
	 *            �ִ� ���� ���̾ �� ��, 4���� ��带 ������ ��� ���̾ �ִ� �Ű���� �����.
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
	 * ������ ���. ���̾ ��� - ��갪�� ������ ���� - ���� ���̾ ���� �� ���� - ��갪�� ������ ����...�� �ݺ��Ͽ� �ΰ��Ű����
	 * �����Ѵ�.
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
	 * ������ ���� ���� ������� ������ ������ ���� - ���̾� ������ - ���� ���̾� ������ ������ ���� - ���� ���̾� �����Ŀ� ���� ��
	 * ����...�� �ݺ��Ͽ� �����ĸ� �����Ѵ�.
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

package neural_network;

import java.util.Random;

public class HiddenLayer {
	Layer[] layer;
	int numOfLayer;
	static double desired;
	static double guess;
	
	HiddenLayer(double[] input, int[] ptron_num, int order, double answer) {
		int i;
		numOfLayer = order + 1;
		desired = answer;
		layer = new Layer[numOfLayer];
		for(i=0; i<order; i++) {
			if(i==0) {
				layer[i] = new Layer(input, ptron_num[i]);
			}
			else {
				layer[i] = new Layer(layer[i-1].outputs, ptron_num[i]);
			}
		}
		layer[i] = new Layer(layer[i-1].outputs, 1);
		guess = layer[i].outputs[0];
	}
	
	HiddenLayer(double[] input, int[] ptron_num, int order) {
		int i;
		numOfLayer = order + 1;
		layer = new Layer[numOfLayer];
		for(i=0; i<order; i++) {
			if(i==0) {
				layer[i] = new Layer(input, ptron_num[i]);
			}
			else {
				layer[i] = new Layer(layer[i-1].outputs, ptron_num[i]);
			}
		}
		layer[i] = new Layer(layer[i-1].outputs, 1);
		guess = layer[i].outputs[0];
	}
	
	static HiddenLayer[] training = new HiddenLayer[50000];
	static HiddenLayer[] test = new HiddenLayer[10];
	static Random random = new Random();
	
	static double f(double x) {
		return 2*x+1;
	}
	
	public static void main(String[] args) {
		int layerNum = 1;	// the number of layer
		int[] eachPtronNum = {1};	// the number of each layer's perceptrons 
		double[] example = new double[2];	// input (x, y)
		int cnt = 0;
		
		//training
		for(int i=0; i<50000; i++) {
			example[0] = random.nextInt()*20;
			example[1] = random.nextInt()*20;
			
			int answer = 1;
			if(example[1]<f(example[0])) answer = -1;
			training[i] = new HiddenLayer(example, eachPtronNum, layerNum, answer);
			for(int j=layerNum; j>=0; j--) {
				training[i].layer[j].adjustWeight(desired, guess);
			}
			/*
			System.out.println(i + ". " + "(x,y) : (" + example[0] + ", "+ example[1] + ")");
			System.out.println("desired : " + desired);
			System.out.println("guess   : " + guess);
			if(i>40000 && guess == desired) {
				cnt++;
				System.out.println("O");
			}
			else System.out.println("X");
			*/
		}
		
		// test
		for(int i=0; i<10; i++) {
			example[0] = random.nextInt()*20;
			example[1] = random.nextInt()*20;
			
			int answer = 1;
			if(example[1]<f(example[0])) answer = -1;
			test[i] = new HiddenLayer(example, eachPtronNum, layerNum);
			System.out.println(i + ". " + "(x,y) : (" + example[0] + ", "+ example[1] + ")");
			System.out.println("desired : " + answer);
			System.out.println("guess   : " + guess);
			if(guess == answer) {
				cnt++;
				System.out.println("O");
			}
			else System.out.println("X");
			System.out.println("");
		}
		System.out.println("        correct answer : " + cnt + "/10");
	}
}

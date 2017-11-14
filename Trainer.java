package neural_network;

import java.util.*;

public class Trainer {
	double[] inputs;
	static Painting p;

	Trainer(double[] input, HiddenLayer m) {
		
		int n = input.length+1;
		inputs = new double[n];
		int i;
		
		for (i=0; i<n; i++) {
			if(i==0) {
				inputs[i] = 1;	//bias
			}
			else {
				inputs[i] = input[i-1];
			}
		}
		m.forwardInput(inputs);
	}
	
	static Trainer[] training = new Trainer[2000];
	static HiddenLayer mormort;
	static Trainer[] testing = new Trainer[10];
	static Random random = new Random();
	int count = 0;

	static double f(double x) {
		return 2*x+1;
	}
	
	
	public static void main(String[] args) {
		int layerNum = 1;	// the number of layer
		int[] eachPtronNum = {2};	// the number of each layer's perceptrons
		double[] example = new double[2];	// input (x, y)
		int cnt = 0;
		
		p = new Painting();
		
		mormort = new HiddenLayer(3, eachPtronNum, layerNum);
		
		//training
		for(int i=0; i<2000; i++) {
			example[0] = random.nextDouble()*640;
			example[1] = random.nextDouble()*360;
	
			double answer = 1;
			if(example[1]<f(example[0])) answer = 0;
			
			training[i] = new Trainer(example, mormort);
			mormort.adjustWeight(answer);
			
			p.swing(example, mormort);
			
		}
		
		
		// test
		for(int i=0; i<10; i++) {
			example[0] = random.nextDouble()*640;
			example[1] = random.nextDouble()*360;
			
			double answer = 1;
			if(example[1]<f(example[0])) answer = 0;
			
			testing[i] = new Trainer(example, mormort);
			
			//p.swing(example, mormort);
			
			System.out.println(i + ". " + "(x,y) : (" + example[0] + ", "+ example[1] + ")");
			System.out.println("desired : " + answer);
			System.out.println("guess   : " + mormort.guess);
			if(mormort.guess == answer) {
				cnt++;
				System.out.println("O");
			}
			else System.out.println("X");
			System.out.println("");
		}
		System.out.println("        correct answer : " + cnt + "/10");
		
	}
}

package ANN;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

public class Painting extends Frame {

	allLayer m;
	trainer t;
	Network network;

	Painting(allLayer mor) {
		m = mor;
		this.setTitle("Graphic");
		this.addWindowListener(new WindowHandler());
		this.setSize(1000, 800);
		this.setVisible(true);

		// Create the Network object
		network = new Network(getWidth() / 2, getHeight() / 2);

		int layers = 2;
		int inputs = 6;
		int hidden = 4;
		
		int i,j;

		Neuron output1 = new Neuron(750, 350);
		Neuron output2 = new Neuron(750, 450);
		for (i = 0; i < layers; i++) {
			for (j = 0; j < inputs; j++) {
				if (i == layers - 1 && hidden < j)
					break;
				if (i == 0) {
					int x = 250;
					int y = 150 + j * 100;
					Neuron n = new Neuron(x, y);
					
					if (i > 0 && hidden > j) {
						for (int k = 0; k < inputs; k++) {
							Neuron prev = network.neurons.get(network.neurons.size() - inputs + k - j);
							network.connect(prev, n);
						}
					}
					
					if (i == layers - 1) {
						network.connect(n, output1);
						network.connect(n, output2);
					}
					network.addNeuron(n);
				}
				if (i == 1) {
					int x = 500;
					int y = 200 + 100 * j;
					Neuron n = new Neuron(x, y);
					
					if (i > 0 && hidden > j) {
						for (int k = 0; k < inputs; k++) {
							Neuron prev = network.neurons.get(network.neurons.size() - inputs + k - j);
							network.connect(prev, n);
						}
					}
					
					if (i == layers - 1) {
						network.connect(n, output1);
						network.connect(n, output2);
					}
					network.addNeuron(n);
				}				
			}
		}
		network.addNeuron(output1);
		network.addNeuron(output2);
		
		renewW();
	}
	
	public void renewW() {
		int i,j,k = 0;
		for(i=0; i<m.layers[0].weight.length; i++) {
			for(j=0; j<m.layers[0].weight[0].length; j++) {
				network.connections.get(k).weight = (float)m.layers[0].weight[i][j];
				k++;
			}
		}
		for(i=0; i<m.layers[1].weight.length; i++) {
			for(j=0; j<m.layers[1].weight[0].length; j++) {
				network.connections.get(k).weight = (float)m.layers[1].weight[i][j];
				k++;
			}
		}
	}

	
	public void swing() {
		repaint();
	}
	

	class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.out.println("Closed");
			System.exit(0);
		}
	}

	public void update(Graphics g) {
		this.paint(g);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		renewW();
		network.paint(g);

	}

}
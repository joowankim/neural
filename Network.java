package ANN;

import java.awt.Point;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

public class Network {

	// The Network has a list of neurons
	ArrayList<Neuron> neurons;

	// The Network now keeps a duplicate list of all Connection objects.
	// This makes it easier to draw everything in this class
	ArrayList<Connection> connections;
	Point position;

	Network(int x, int y) {
		position = new Point(x, y);
		neurons = new ArrayList<Neuron>();
		connections = new ArrayList<Connection>();
	}

	// We can add a Neuron
	public void addNeuron(Neuron n) {
		neurons.add(n);
	}

	// We can connection two Neurons
	public void connect(Neuron a, Neuron b) {
		Connection c = new Connection(a, b);
		a.addConnection(c);
		// Also add the Connection here
		connections.add(c);
	}

	public void paint(Graphics g) {
	
		for (Connection c : connections) {
			c.paint(g);
		}
		
		for (Neuron n : neurons) {
			n.paint(g);
		}

		
	}

}

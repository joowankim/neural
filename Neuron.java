package ANN;

import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

public class Neuron {
	
	// Position of point
	Point position;
	
	// fill or not fill
	int fill = 1;

	// Neuron has a list of connections
	ArrayList<Connection> connections;

	// The Neuron's size can be animated
	int r = 32;

	Neuron(int x, int y) {
		position = new Point(x, y);
		connections = new ArrayList<Connection>();
	}

	// Add a Connection
	public void addConnection(Connection c) {
		connections.add(c);
	}
	
	public void paint(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  
		  if(fill == 1)
			  g.fillOval(position.x, position.y, r, r);
		  else
			  g.drawOval(position.x, position.y, r, r);
	  }

	
}

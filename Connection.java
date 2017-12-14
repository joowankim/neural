package ANN;

import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

public class Connection {

	// Connection is from Neuron A to B
	Neuron a;
	Neuron b;

	// Connection has a weight
	float weight;

	Connection(Neuron from, Neuron to) {
		a = from;
		b = to;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if(weight < 0) {
			weight = (float)Math.sqrt(weight*weight);
			a.fill = 0;
		}
		else a.fill = 1;
		g2.setStroke(new BasicStroke(weight * 3, BasicStroke.CAP_ROUND, 0));
		g2.draw(new Line2D.Double(a.position.x + a.r/2, a.position.y + a.r/2, b.position.x + b.r/2, b.position.y + b.r/2));
	}

}

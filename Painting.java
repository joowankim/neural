package neural_network;

import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Painting extends Frame{
	int x, y;
	HiddenLayer m;
	Trainer t;
	Vector<double[]> vector = new Vector<double[]>(2000);
	Point p;

	public void swing(double[] p, HiddenLayer mor) {
		this.x = (int)p[0];
		this.y = (int)p[1];
		vector.addElement(p);
		m = mor;
		
		this.setTitle("Graphic");
		this.addWindowListener(new WindowHandler());
		this.setSize(640, 360);
		this.setVisible(true);
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
		Graphics2D g2 = (Graphics2D)g;

		for (int i=0; i<vector.size(); i++) {
			t = new Trainer(vector.elementAt(i), m);
			
			if (m.guess>0) {
				g.fillOval(x, y, 4, 4);
				//g2.draw(new Line2D.Double(-0.5, 0, 179.5, 360));
				//찾아가는 직선을 표현할 예정
			}
			else {
				g.drawOval(x, y, 4, 4);
				//g2.draw(new Line2D.Double(-0.5, 0, 179.5, 360));
			}
		}
		g2.setStroke(new BasicStroke(2));
		g2.draw(new Line2D.Double(-0.5, 0, 179.5, 360));
	
	}
}



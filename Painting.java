package neural_network;

import java.util.Vector;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

public class Painting extends Frame{
	double[] P;
	HiddenLayer m;
	Trainer t;
	static Vector<double[]> vector = new Vector<double[]>(2000);

	Painting (){
		this.setTitle("Graphic");
		this.addWindowListener(new WindowHandler());
		this.setSize(800, 450);
		this.setVisible(true);
	}
	
	public void swing(double[] p, HiddenLayer mor) {
		P = new double[2];
		this.P[0] = p[0];
		this.P[1] = p[1];
		vector.add(P);
		m = mor;

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
			t = new Trainer(vector.elementAt(i));
			
			if (m.guess>0) {
				g.fillOval((int)vector.elementAt(i)[0], (int)vector.elementAt(i)[1], 4, 4);
				//g2.draw(new Line2D.Double(-0.5, 0, 179.5, 360));
				//찾아가는 직선을 표현할 예정
			}
			else {
				g.drawOval((int)vector.elementAt(i)[0], (int)vector.elementAt(i)[1], 4, 4);
				//g2.draw(new Line2D.Double(-0.5, 0, 179.5, 360));
			}
	
		}
		//g2.setStroke(new BasicStroke(2));
		//g2.draw(new Line2D.Double(-0.5, 0, 179.5, 360));
		g2.setStroke(new BasicStroke(2));
		g2.draw(new Line2D.Double(0, 0, 450, 450));
	
	}
}

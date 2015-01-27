import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.*;


public class BarView extends JFrame {
	BarGraph barGraph;
	
	public BarView(SimulatorView view) {
		setTitle("BarView");
		barGraph = new BarGraph(view);
		barGraph.setPreferredSize(new Dimension(400,800));
		Container contents = getContentPane();
		contents.add(barGraph);
		pack();
		setVisible(true);
		
	}
	
	public void update(Slice[] slices) {
		barGraph.update(slices);
	}
	
}
class BarGraph extends JComponent {
	Graphics g;
	SimulatorView view;
	Slice[] slices;
	
	public void update(Slice[] slices) {
		this.slices = slices;
		this.repaint();
	}
	
	public BarGraph(SimulatorView view) {
		this.view = view;
		
	}
	
	public void paint(Graphics g) {
	  this.g = g;
      drawBars((Graphics2D) g, getBounds(), slices);
    }
	
	void drawBars(Graphics2D g, Rectangle area, Slice[] slices) {
		
		int total = 0;
		int deltaX = area.width / slices.length;
		
		for (int i = 0; i < slices.length; i++) {
			total = (int) (total + slices[i].value);
		}
		for (int i = 0; i< slices.length; i++) {
			double height = total / slices[i].value;
			int heightOfBar = (int) (area.height / height);
			
			g.setColor(slices[i].color);
			g.fillRect(i * deltaX,area.height - heightOfBar ,deltaX ,heightOfBar);
		}
	}
}

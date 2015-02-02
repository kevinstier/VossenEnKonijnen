import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.*;

public class BarGraph extends JComponent {
	Graphics g;
	Slice[] slices = null;
	
	public void update(Slice[] slices) {
		this.slices = slices;
		this.repaint();
	}
	
	public BarGraph() {}
	
	public void paint(Graphics g) {
		if(slices != null) {
		  this.g = g;
	      drawBars((Graphics2D) g, getBounds(), slices);
		}
    }
	
	private void drawBars(Graphics2D g, Rectangle area, Slice[] slices) {
		
		int total = 0;
		int deltaX = area.width / slices.length;
		
		for (int i = 0; i < slices.length; i++) {
			total = (int) (total + slices[i].value);
		}
		for (int i = 0; i< slices.length; i++) {
			double height = total / slices[i].value;
			int heightOfBar = (int) (area.height / height);
			
			g.setColor(slices[i].color);
			g.fillRect((i * deltaX) + 10,area.height - heightOfBar ,deltaX - 10 ,heightOfBar);
		}
	}
}

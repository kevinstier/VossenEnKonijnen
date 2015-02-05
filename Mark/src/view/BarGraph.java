package view;

import model.Slice;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.*;

/**
 * A graphic representation of the animals and hunters displayed in a bar graph.
 * 
 * @author Mark Nijboer
 * @version 2015.2.3
 */
public class BarGraph extends JComponent {
	Graphics g;
	Slice[] slices = null;
	
	
	/**
     * Updates the bar graph with value's given in Slice objects
     * @param Slice[] an array of slice objects.
     */
	public void update(Slice[] slices) {
		this.slices = slices;
		this.repaint();
	}
	
	/**
     * Paints the bars on the component
     * @param Graphics.
     */
	public void paint(Graphics g) {
		if(slices != null) {
		  this.g = g;
	      drawBars((Graphics2D) g, getBounds(), slices);
		}
    }
	
	/**
     * Draws bars depending on the amount of slices
     * @param g		A Graphics2D object.
     * @param area	The surface to draw on.
     * @param slices An array of Slice objects containing the information to display.
     */
	private void drawBars(Graphics2D g, Rectangle area, Slice[] slices) {
		
		int total = 0;
		int deltaX = area.width / slices.length;
		
		for (int i = 0; i < slices.length; i++) {
			total = (int) (total + slices[i].getValue());
		}
		for (int i = 0; i< slices.length; i++) {
			double height = total / slices[i].getValue();
			int heightOfBar = (int) (area.height / height);
			
			g.setColor(slices[i].getColor());
			g.fillRect(i * deltaX,area.height - heightOfBar ,deltaX ,heightOfBar);
		}
	}
}

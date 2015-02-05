package view;

import model.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * A class to make a JComponent containing a pie chart
 * 
 * @author Mark Nijboer
 * @version 2015.2.3
 */
class PieChart3 extends JComponent {
	private Graphics graphics;
	private Slice[] slices = {new Slice(10, Color.black)};
   
	/**
	 * Method to bind an event for updating the pie chart.
	 * 
	 * @param g Graphics
	 */
   public void paint(Graphics g) {
	  this.graphics = g;
      drawPie((Graphics2D) g, getBounds(), slices);
   }
   
   /**
    * Method to draw the pie chart.
    * 
    * @param g Graphics to draw on
    * @param area The available area.
    * @param slices The information to draw a pie containing the values and colors.
    */
   void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
      double total = 0.0D;
      for (int i = 0; i < slices.length; i++) {
         total += slices[i].getValue();
      }
      double curValue = 0.0D;
      int startAngle = 0;
      for (int i = 0; i < slices.length; i++) {
         startAngle = (int) (curValue * 360 / total);
         int arcAngle = (int) (slices[i].getValue() * 360 / total);
         try {
	         g.setColor(slices[i].getColor());
	         g.fillArc(area.x, area.y, area.width, area.height, 
	         startAngle, arcAngle);
         } catch(Exception e) {}
         curValue += slices[i].getValue();
      }
   }
   
   /**
    * Method to update the pie chart with new values
    * @param slices Containing the new values.
    */
   public void updateChart(Slice[] slices) {
	   this.slices = slices;
	   this.repaint();
	   drawPie((Graphics2D) this.graphics, getBounds(), slices);
   }
}
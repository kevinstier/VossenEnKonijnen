import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

class Slice {
   double value;
   Color color;
   public Slice(double value, Color color) {  
      this.value = value;
      this.color = color;
   }
   public double getValue() {
	   return this.value;
   }
   public Color getColor() {
	   return this.color;
   }
}
class PieChart3 extends JComponent {
	Graphics graphics;
	
	private Slice[] slices = {new Slice(10, Color.black)};
   PieChart3() {}
   
   public void paint(Graphics g) {
	  this.graphics = g;
      drawPie((Graphics2D) g, getBounds(), slices);
   }
   void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
      double total = 0.0D;
      for (int i = 0; i < slices.length; i++) {
         total += slices[i].value;
      }
      double curValue = 0.0D;
      int startAngle = 0;
      for (int i = 0; i < slices.length; i++) {
         startAngle = (int) (curValue * 360 / total);
         int arcAngle = (int) (slices[i].value * 360 / total);
         g.setColor(slices[i].color);
         g.fillArc(area.x, area.y, area.width, area.height, 
         startAngle, arcAngle);
         curValue += slices[i].value;
      }
   }
   
   public void updateChart(Slice[] slices) {
	   this.slices = slices;
	   this.repaint();
	   drawPie((Graphics2D) this.graphics, getBounds(), slices);
   }
}
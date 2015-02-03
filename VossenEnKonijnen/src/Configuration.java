import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Create the sliders and initialize them
 * @author Ronald Elzen
 *
 */

public class Configuration extends JPanel{
	private JSlider a;
	private JLabel b;
	
	public Configuration(String labelText, int boundsMin,int boundsMax,int d) {
	
		a = new JSlider(JSlider.HORIZONTAL, boundsMin, boundsMax, d);
		b = new JLabel(labelText);
	    
		 a.setMajorTickSpacing(10);
		 a.setMinorTickSpacing(1);
		 a.setPaintTicks(true);
		 a.setPaintLabels(true);
	
		 
		 add(b);
		 add(a);
	}
	
	/**
	 * Get the value of the slider
	 * @return the value
	 */
	public int getValue() {
		return a.getValue();
	}
	
	/**
	 * Set the value of the sliders
	 * @param value
	 */
	public void setValue(int value)
	{
		a.setValue(value);
	}
	
}

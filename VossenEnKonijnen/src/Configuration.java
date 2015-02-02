import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


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
	
	public int getValue() {
		return a.getValue();
	}
	
	public void setValue(int value)
	{
		a.setValue(value);
	}
	
}

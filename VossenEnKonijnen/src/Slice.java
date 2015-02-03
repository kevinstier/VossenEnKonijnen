import java.awt.Color;

/**
 * Slices containing values and corresponding colors.
 * 
 * @author Mark Nijboer
 * @version 2015.02.3
 */
public class Slice {
	// The value to display
	private double value;
	// The color that belongs to that animal
	private Color color;
	
	/**
	 * Constructor
	 * 
	 * Store the parameters in the object.
	 * 
	 * @param value The value
	 * @param color The corresponding color
	 */
	public Slice(double value, Color color) {  
		this.value = value;
		this.color = color;
	}
	/**
	 * Getter for the value
	 * @return value
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Getter for the color
	 * @return color
	 */
	public Color getColor() {
		return this.color;
	}
}

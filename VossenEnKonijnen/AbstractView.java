import java.awt.Color;

/**
 * A graphical view of the simulation grid. This interface defines all possible different
 * views.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public abstract class AbstractView
{
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    abstract void setColor(Class animalClass, Color color);

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    abstract boolean isViable(Field field);

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    abstract void showStatus(int step, Field field);
    
    /**
     * Prepare for a new run.
     */
    abstract void reset();
}
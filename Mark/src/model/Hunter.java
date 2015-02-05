package model;

import view.*;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;


public class Hunter implements Actor {
	
	// The hunter's field.
    private Field field;
    // The hunter's position in the field.
    private Location location;
    // Number of kills made
    private int bullets = 0;
    // Number of times waited
    private int wait = 0;
    // Color of the hunter
    private Color color;
    // Color of the hunter
    private Color HUNTER_COLOR = Color.red;

	/**
     * Create a hunter. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location location)
    {
    	color = Color.red;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * This is what the hunter does most of the time: it hunts for
     * animals. In the process, it might kill an animal.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born hunters.
     */
    public void act(List<Actor> newHunters)
    {

        if(isAlive()) {      
            // Move towards a source of food if found.
            Location newLocation = findAnimal();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = this.field.freeAdjacentLocation(this.location);
                if(newLocation == null) {
                	newLocation = location; // Stay put
                }
            }
            
            setLocation(newLocation);
            
        }
    }
    
    /**
     * Get the current hunter color
     * @return the color
     */
    public Color getColor() {
    	return color;
    }
    
    /**
     * Returns the official color linked to hunter.
     * @return	The hunters official color
     */
    public Color getOfficialColor() {
    	return HUNTER_COLOR;
    }
    
    /**
     * Set the location of the hunter
     * @param newLocation of the hunter
     */
    private void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Look for animals adjacent to the current location.
     * Only the first live rabbit is shot.
     * @return 
     * @return Where animal was found, or null if it wasn't.
     */
    private Location findAnimal()
    {
        Field field = this.field;
        List<Location> adjacent = field.adjacentLocations(this.location);
        Iterator<Location> it = adjacent.iterator();
        if(wait == 0) {
        	if(bullets < SimulatorView.getBulletLimitHunter()) {
		        while(it.hasNext()) {
		            Location where = it.next();
		            Object animal = field.getObjectAt(where);
		            if(animal instanceof Rabbit) {
		                Rabbit rabbit = (Rabbit) animal;
		                if(rabbit.isAlive()) { 
		                    rabbit.setDead();
		                    // Remove the dead rabbit from the field.
		                    bullets++;
		                    return where;
		                }
		            }
		                if(animal instanceof Fox) {
		                    Fox fox = (Fox) animal;
		                    if(fox.isAlive()) { 
		                        fox.setDead();
		                        // Remove the dead rabbit from the field.
		                        bullets++;
		                        return where;
		                    }
		                }
		                else if(animal instanceof Bear) {
		                    Bear bear = (Bear) animal;
		                    if(bear.isAlive()) { 
		                        bear.setDead();
		                        // Remove the dead rabbit from the field.
		                        bullets++;
		                        return where;
		                        }
		            }
		        }
        	} else {
        		wait = SimulatorView.getWaitLimitHunter(); // 2 + 1 = waiting 3 turns
        		color = Color.pink;
        		
        	}
        } else {
        	wait--;
        	if(wait == 0) {
        		color = Color.red;
        		bullets = 0;
        	}
        }
        return null;
    }
	
    @Override
	public boolean isAlive() {
		return true;
	}

}

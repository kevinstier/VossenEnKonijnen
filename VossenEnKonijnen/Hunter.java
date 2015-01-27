import java.util.Iterator;
import java.util.List;


public class Hunter implements Actor {
	
	// The hunter's field.
    private Field field;
    // The hunter's position in the field.
    private Location location;

	/**
     * Create a hunter. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location location)
    {
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
            }
            
            setLocation(newLocation);
            
        }
    }
    
    /**
     * Look for animals adjacent to the current location.
     * Only the first live rabbit is shot.
     * @return 
     * @return Where animal was found, or null if it wasn't.
     */
    private void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    private Location findAnimal()
    {
        Field field = this.field;
        List<Location> adjacent = field.adjacentLocations(this.location);
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
                if(animal instanceof Fox) {
                    Fox fox = (Fox) animal;
                    if(fox.isAlive()) { 
                        fox.setDead();
                        // Remove the dead rabbit from the field.
                        return where;
                    }
                }
                else if(animal instanceof Bear) {
                    Bear bear = (Bear) animal;
                    if(bear.isAlive()) { 
                        bear.setDead();
                        // Remove the dead rabbit from the field.
                        return where;
                        }
            }
        }
        return null;
    }
	

	public boolean isAlive() {
		// TODO Auto-generated method stub
		return true;
	}

}

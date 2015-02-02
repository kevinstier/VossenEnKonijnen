import java.awt.Color;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a Bear.
 * Bears age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Bear extends Animal
{
    // Characteristics shared by all Bears (class variables).

    // The age to which a Bear can live.
    private static int max_age;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The Bear's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a Bear. A Bear can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Bear will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bear(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        color = Color.black;
        OFFICIAL_COLOR = Color.black;
        if(randomAge) {
        	max_age = SimulatorView.getLifeTimeBear();
            setAge(rand.nextInt(max_age));
            foodLevel =  rand.nextInt(SimulatorView.getFoodValueBear());
        }
        else {
            setAge(0);
            foodLevel = SimulatorView.getFoodValueBear();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void giveBirth(List<Actor> newBears)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Bear young = new Bear(false, field, loc);
            newBears.add(young);
        }
    }
    
    /**
     * This is what the Bear does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newBeares A list to return newly born Bears.
     */
    public void act(List<Actor> newBears)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newBears);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    
    /**
     * Make this Bear more hungry. This could result in the Bear's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Fox) {
				Fox fox = (Fox) animal;
				if(fox.isAlive()) {
					fox.setDead();
					foodLevel = SimulatorView.getFoodValueBear();
					// Remove the dead fox from the field.
					return where;
				}
            }
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = SimulatorView.getFoodValueBear();
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Return the current color of the rabbit
     * @return the color
     */
    public Color getColor() {
    	return color;
    }
    
    /**
     * Return the regular color of the rabbit
     * @return the color
     */
    public Color getOfficialColor()
    {
    	return OFFICIAL_COLOR;
    }

    @Override
	protected int getBreedingAge() {
		return SimulatorView. getBreedAgeBear();
	}

	@Override
	protected int getMaxAge() {
		return SimulatorView.getLifeTimeBear();
	}

	@Override
	protected double getBreedingProbability() {
		return ((double) SimulatorView.getBreedProbabilityBear()) / 100;
	}


	@Override
	protected int getMaxLitterSize() {
		return SimulatorView.getgetLitterSizeBear();
	}
	
	@Override
	public boolean isAlive() 
	{
		return alive;
	}
   
}
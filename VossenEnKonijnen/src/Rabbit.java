import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private static int GRASS_FOOD_VALUE = 5;
    private int foodLevel;
    
    
    // Individual characteristics (instance fields).
    

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
    	color = Color.orange;
        setAge(0);
        if(randomAge) {
        	setAge(rand.nextInt(MAX_AGE));
        }
        foodLevel = GRASS_FOOD_VALUE;
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
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
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    private Location findFood()
	{
		Field field = getField();
		List<Location> adjacent = field.adjacentLocations(getLocation());
		Iterator<Location> it = adjacent.iterator();
		while(it.hasNext()) {
			Location where = it.next();
			Object animal = field.getObjectAt(where);
			if(animal instanceof Grass) {
				Grass grass = (Grass) animal;
				if(grass.isAlive()) {
					grass.setDead();
					foodLevel = GRASS_FOOD_VALUE;
					// Remove the dead piece of grass from the field.
					return where;
				}
			}
		}
		return null;
	}


    public Color getColor() {
    	return color;
    }

	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}
	
	@Override
	protected int getMaxAge() {
		return MAX_AGE;
	}
	
	@Override
	protected double getBreedingProbability() {
		return BREEDING_PROBABILITY;
	}
	
	@Override
	protected int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}
	
	@Override
	public boolean isAlive() 
	{
		return alive;
	}
}

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal implements Actor
{
	// Whether the animal is alive or not.
    protected boolean alive = true;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age
    private int age;
 // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private int infected = 0;
    
    protected Color color;
    
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newActors);


    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    
    public Color getColor() {
    	return color;
    }
    
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    
    /**
     * Een dier kan zich voortplanten als het de 
     * voortplantingsleeftijd heeft bereikt.
     * @return true als het dier zich kan voortplanten
     */
    public boolean canBreed()
    {
    	return age >= getBreedingAge();
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    protected void giveBirth(List<Actor> newAnimals)
    {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
    	Animal animal = this;
    	Animal nieuw = null;
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            if (animal instanceof Rabbit) {
            	nieuw = new Rabbit(false, field, loc, infected);
            }
            else if (animal instanceof Fox) {
            	nieuw = new Fox(false, field, loc);
            }
            else if (animal instanceof Bear) {
            	nieuw = new Bear(false, field, loc);
            }
            newAnimals.add(nieuw);
        }
    }
    
    /**
	 * Retourneer de voortplantingskans van dit dier.
	 */
	abstract protected double getBreedingProbability();

	/**
	 * Returns the maximum amount of offspring an animal can have.
	 */
	abstract protected int getMaxLitterSize();

    
    /**
     * Retourneer de voortplantingsleeftijd van dit dier.
     * @return De voortplantingsleeftijd van dit dier.
     */
    abstract protected int getBreedingAge();
    
    /**
     * Retourneer de maximum leeftijd van dit dier.
     * @return De maximum leeftijd van dit dier.
     */
    abstract protected int getMaxAge();

    /**
     * Return the animal's age.
     * @return The animal's age.
     */
	public int getAge() {
		return age;
	}

	/**
     * Set the animal's age.
     * @param The animal's age.
     */
	public void setAge(int age) {
		this.age = age;
	}

	public boolean isAlive() {
		return false;
	}


}

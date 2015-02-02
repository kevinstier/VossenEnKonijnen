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
    private double breedingProbabilty = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private final double INFECTION_CHANCE = 0.9;
    private boolean ziek;
    private final int FIRST_INFECTED_CHANCE = 10;
    private final int FIRST_INFECTED = 500;
    private int timeSick = 0;
    private final int MAX_TIME_SICK = 5;    
    private int counter;
    private final int MIN_FREE_SPACE = 2;
    private final double LESS_BREEDING_PROBABILITY = 0.5;
    
    
    // Individual characteristics (instance fields).
    

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location, int numberInfected)
    {
        super(field, location);
    	color = Color.orange;
    	OFFICIAL_COLOR = Color.orange;
        
        if(randomAge) {
        	setAge(rand.nextInt(MAX_AGE));
        } else {
        	setAge(0);
        }
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(100);
    	if( random <= FIRST_INFECTED_CHANCE && numberInfected < FIRST_INFECTED)
    	{
    		setZiekteGen(true);
    	}
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newRabbits)
    {
    	if (getZiekteGen()){ 
	    	if (timeSick < MAX_TIME_SICK) {
	            timeSick++;
	    	} else {
	    		setDead();
	    	}
    	}
	        incrementAge();
	        if(isAlive()) {
	        	checkFood();
	        	if (counter >= MIN_FREE_SPACE) {
	        		setBreedingProbabilty(LESS_BREEDING_PROBABILITY * this.breedingProbabilty);
	        		counter = 0;
	        	}
	        	if (neighborInfected()) {
		        	if (ziekteGen()) {
	            		setZiekteGen(true);
	             	}
	        	}
	        	if (!getZiekteGen()) {
	        		giveBirth(newRabbits);   
	        	}
	            // Move towards a source of food if found.
	            Location newLocation = getField().freeAdjacentLocation(getLocation());

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
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc, Simulator.getInfected());
            newRabbits.add(young);
        }
    }
    
    public void checkFood()
    {
    	Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    counter++;
                }
            }
        }
    }
    
    public boolean ziekteGen()
    {
    	int random = (rand.nextInt(100) + 1) / 100;
    	if( random <= INFECTION_CHANCE)
    	{
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean neighborInfected()
    {
    	 Field field = getField();
         List<Location> adjacent = field.adjacentLocations(getLocation());
         Iterator<Location> it = adjacent.iterator();
         while(it.hasNext()) {
             Location where = it.next();
             Object animal = field.getObjectAt(where);
             if(animal instanceof Rabbit) {
                 Rabbit rabbit = (Rabbit) animal;
                 if(rabbit.getZiekteGen()) {
                 	return true;
                 }
             }
         }
         return false;
    }
    
    public void setBreedingProbabilty(double breedingProbabilty)
    {
    	this.breedingProbabilty = breedingProbabilty;
    }
    
    public boolean getZiekteGen()
    {
    	return ziek;
    }

    public void setZiekteGen(boolean ziek)
    {
    	this.ziek = ziek;
     	color = Color.cyan;
    }

    public Color getColor() {
    	return color;
    }
    
    public Color getOfficialColor()
    {
    	return OFFICIAL_COLOR;
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
		return breedingProbabilty;
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

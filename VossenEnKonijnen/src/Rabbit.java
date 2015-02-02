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
	
    // The age to which a rabbit can live.
    private static int max_age;
    // The likelihood of a rabbit breeding.
    private double breedingProbability;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // A boolean to see whether the rabbit is sick or not
    private boolean ziek;
    // The chance the rabbit is one of the first to be infected
    private final int FIRST_INFECTED_CHANCE = 20;
    // Maximum of first infected rabbits
    private final int FIRST_INFECTED = 500;
    // Integer for how many steps the rabbit has been sick
    private int timeSick = 0;
    // Count neighbors
    private int counter;
    // Minimum free space required around the rabbit
    private final int MIN_FREE_SPACE = 2;
    // Factor to reduce breeding probability
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
    	setBreedingProbability(((double) SimulatorView.getBreedProbabilityRabbit()) / 100);
        
    	max_age = SimulatorView.getLifeTimeRabbit();
        if(randomAge) {
          	setAge(rand.nextInt(max_age));
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
    @Override
	public void act(List<Actor> newRabbits)
    {
    	if (getZiekteGen()){ 
	    	if (timeSick < SimulatorView.getStepsBeforeDeath()) {
	            timeSick++;
	    	} else {
	    		setDead();
	    	}
    	}
	        incrementAge();
	        if(isAlive()) {
	        	checkFood();
	        	if (counter >= MIN_FREE_SPACE) {
	        		setBreedingProbability(LESS_BREEDING_PROBABILITY * this.breedingProbability);
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
    @Override
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
    
    /**
     * Check whether the rabbit has enough space around it
     */
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
    
    /**
     * Check whether the rabbit will get infected or not
     * @return boolean true if yes
     * @return boolean false if not
     */
    public boolean ziekteGen()
    {
    	int random = (rand.nextInt(100) + 1) / 100;
    	if( random <= ((double) SimulatorView.getInfectionChance()) / 100)
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
    
    /**
     * Set the rabbit's breeding probability.
     * @param the rabbit's breeding probability.
     */
    public void setBreedingProbability(double breedingProbability)
    {
    	this.breedingProbability = breedingProbability;
    }
    
    /**
     * Return if the rabbit is sick
     * @return true if the rabbit is sick
     * @return false if the rabbit is not sick
     */
    public boolean getZiekteGen()
    {
    	return ziek;
    }

    /**
     * Make the rabbit sick
     * @param true if the rabbit gets sick
     * @param false if the rabbit doesn't get sick
     */
    public void setZiekteGen(boolean ziek)
    {
    	this.ziek = ziek;
     	color = Color.cyan;
    }

    /**
     * Return the current color of the rabbit
     * @return the color
     */
    @Override
	public Color getColor() {
    	return color;
    }
    
    /**
     * Return the regular color of the rabbit
     * @return the color
     */
    @Override
	public Color getOfficialColor()
    {
    	return OFFICIAL_COLOR;
    }

    @Override
	protected int getBreedingAge() {
		return SimulatorView.getBreedAgeRabbit();
	}

	@Override
	protected int getMaxAge() {
		return SimulatorView.getLifeTimeRabbit();
	}

	@Override
	protected double getBreedingProbability() {
		return breedingProbability;
	}

	
	@Override
	protected int getMaxLitterSize() {
		return SimulatorView.getLitterSizeRabbit();
	}
	
	@Override
	public boolean isAlive() 
	{
		return alive;
	}
}

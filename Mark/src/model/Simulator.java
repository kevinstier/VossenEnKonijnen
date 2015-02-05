package model;

import view.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // List of animals in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // A pie chart of the simulation.
    private MonitorView monitorView;
    // The fieldstats for the simulation
    private FieldStats stats;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<Actor>();
        field = new Field(depth, width);

        
        
        // Create a view of the state of each location in the field.
        stats = new FieldStats();
        
        monitorView = new MonitorView();
        TextView textView = stats.getTextView();
        
        view = new SimulatorView(depth, width, stats, monitorView, textView);
        
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public boolean simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            if(!simulateOneStep()) {
            	totalExtinction();
            	return false;
            }
        }
        return true;
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public boolean simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<Actor>();         
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActors);

        view.showStatus(step, field);
        
        monitorView.update(actors, step);
        
        if(checkTotalExtinction(actors)) {
        	return false;
        }
        
        return true;
    }
    
    public boolean checkTotalExtinction(List<Actor> actors) {
    	
    	for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            if(!(actor instanceof Hunter)) {
            	return false;
            }
        }
    	return true;
    }
    
    public void totalExtinction() {
    	JOptionPane.showMessageDialog(null, "GAME OVER!\nOnly hunters are alive. Reset the simulation to start again.", "Total Extinction", JOptionPane.WARNING_MESSAGE);
    	view.disableButtons();
    	view.getResetButton().setEnabled(true);
    }
    
    /**
     * Get the simulatorview
     * @return the simulatorview
     */
    public SimulatorView getSimulatorView() {
    	return this.view;
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        stats.hardReset();
        
        view.enableButtons();
        // Show the starting state in the view.
        view.showStatus(step, field);
        
        monitorView.update(actors, step);
        
        // Reset the sliders
        SimulatorView.lifeTimeRabbit.setValue(40);
        SimulatorView.lifeTimeFox.setValue(90);
        SimulatorView.lifeTimeBear.setValue(40);
        SimulatorView.breedAgeRabbit.setValue(5);
        SimulatorView.breedAgeFox.setValue(15);
        SimulatorView.breedAgeBear.setValue(4);
        SimulatorView.litterSizeRabbit.setValue(4);
        SimulatorView.litterSizeFox.setValue(6);
        SimulatorView.litterSizeBear.setValue(4);
        SimulatorView.breedProbabilityRabbit.setValue(80);
        SimulatorView.breedProbabilityFox.setValue(8);
        SimulatorView.breedProbabilityBear.setValue(2);
        SimulatorView.foodValueFox.setValue(9);
        SimulatorView.foodValueBear.setValue(11);
        SimulatorView.waitLimitHunter.setValue(7);
        SimulatorView.bulletLimitHunter.setValue(3);
        SimulatorView.infectionChance.setValue(90);
        SimulatorView.stepsBeforeDeath.setValue(5);
    }
    
    public void makeRandomRabbitIll(int times) {
    	ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>();         
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            if(actor instanceof Rabbit) {
            	Rabbit rabbit = (Rabbit) actor;
                rabbits.add(rabbit);
            }
        }
        for (int i = 0; i < times; i++) {
	        int length = rabbits.size();
	        if(length > 0) {
	        	Random r = new Random();
	        	int low = 1;
	        	int high = length + 1;
	        	int randomNumber = r.nextInt(high-low) + low;
	        	Rabbit chosenRabbit = rabbits.get(randomNumber);
	        	if (chosenRabbit.getZiekteGen()){
	        		i--;
	        	} else {
		        	chosenRabbit.setZiekteGen(true);
		        	view.showStatus(step, field);
	        	}
	        }
        }
    }
    
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        int huntersCreated = 0;
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= ((double) SimulatorView.getCreationChanceFox()) / 100) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= ((double) SimulatorView.getCreationChanceRabbit()) / 100) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= ((double) SimulatorView.getCreationChanceBear()) / 100) {
                    Location location = new Location(row, col);
                    Bear bear = new Bear(true, field, location);
                    actors.add(bear);
                }
                else if(rand.nextDouble() <= (((double) SimulatorView.getCreationChanceHunter()) / 100) && huntersCreated < SimulatorView.getMaximumAmountHunters()) {
                	huntersCreated++;
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(field, location);
                    actors.add(hunter);
                }
                // else leave the location empty.
            }
        }
    }
}

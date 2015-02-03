import java.awt.*;

import javax.swing.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    // Initiate labels
    private JLabel stepLabel, population, legend;
    // Initiate buttons
    private JButton step1, step100, step1000, reset;
    // Fieldview
    private FieldView fieldView;
    // Icon
    private Icon logoImage;
    // Container
    private Container contents;
    
    // Initiate the sliders
    public static Configuration creationChanceRabbit;
    public static Configuration creationChanceFox;
    public static Configuration creationChanceBear;
    public static Configuration creationChanceHunter;
    public static Configuration maximumAmountHunters;
    public static Configuration lifeTimeRabbit;
    public static Configuration lifeTimeFox;
    public static Configuration lifeTimeBear;
    public static Configuration breedAgeRabbit;
    public static Configuration breedAgeFox;
    public static Configuration litterSizeRabbit;
    public static Configuration litterSizeFox;
    public static Configuration litterSizeBear;
    public static Configuration breedAgeBear;
    public static Configuration breedProbabilityRabbit;
    public static Configuration breedProbabilityFox;
    public static Configuration breedProbabilityBear;
    public static Configuration foodValueFox;
    public static Configuration foodValueBear;
    public static Configuration bulletLimitHunter;
    public static Configuration waitLimitHunter;
    public static Configuration infectionChance;
    public static Configuration stepsBeforeDeath;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width, FieldStats fieldStats, MonitorView monitorView, TextView textView)
    {
        stats = fieldStats;
        colors = new LinkedHashMap<Class, Color>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setResizable(false);

        setTitle("Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        
        logoImage = new ImageIcon(Simulator.class.getResource("/Legenda3.png"));
        legend = new JLabel(logoImage, JLabel.CENTER);
        
        JPanel panelleft = new JPanel(new BorderLayout());
        step1 = new JButton("Step 1");
        step100 = new JButton("Step 100");
        step1000 = new JButton("Step 1000");
        reset = new JButton("Reset");
        
        JPanel steps = new JPanel(new GridBagLayout());
        GridBagConstraints cst = new GridBagConstraints();
        cst.fill = GridBagConstraints.BOTH;
        
        // Make the sliders
        creationChanceRabbit = new Configuration("Creation chance Rabbit",0,100,8);
        creationChanceFox = new Configuration("Creation chance Fox",0,100,2);
        creationChanceBear = new Configuration("Creation chance Bear",0,100,1);
        creationChanceHunter = new Configuration("Creation chance Hunter",0,100,1);
        maximumAmountHunters = new Configuration("Maximum amount Hunter",0,100,30);
        lifeTimeRabbit = new Configuration("Lifetime Rabbit",0,100,40);
        lifeTimeFox = new Configuration("Lifetime Fox",0,100,90);
    	lifeTimeBear = new Configuration("Lifetime Bear",0,100,40);
    	breedAgeRabbit  = new Configuration("Breed age Rabbit",0,20,5);
        breedAgeFox = new Configuration("Breed age Fox",0,20,15);
    	breedAgeBear = new Configuration("Breed age Bear",0,20,4);
        breedProbabilityRabbit = new Configuration("Breed probability Rabbit",0,100,80);
        breedProbabilityFox = new Configuration("Breed probability Fox",0,100,8);
    	breedProbabilityBear = new Configuration(" Breed probability Bear",0,100,2);
        litterSizeRabbit = new Configuration("Litter size Rabbit",0,10,4);
        litterSizeFox = new Configuration("Litter size Fox",0,10,6);
    	litterSizeBear = new Configuration("Litter size Bear",0,10,4);
    	foodValueFox = new Configuration("Food value Fox",0,20,9);
    	foodValueBear = new Configuration("Food value Bear",0,20,11);
    	bulletLimitHunter = new Configuration("Bullet limit Hunter",0,20,7);
    	waitLimitHunter = new Configuration("Wait limit Hunter",0,20,3);
    	infectionChance = new Configuration("Infection chance Rabbit",0,100,90);
    	stepsBeforeDeath = new Configuration("Steps before death Rabbit",0,10,5);
        
        cst.gridx = 0;
        cst.gridy = 0;
        cst.weightx = 1.0;
        cst.weighty = 1.0;
        steps.add(step1, cst);
        
        cst.gridx = 0;
        cst.gridy = 1;
        cst.weightx = 1.0;
        cst.weighty = 1.0;
        steps.add(step100, cst);
        
        cst.gridx = 0;
        cst.gridy = 2;
        cst.weightx = 1.0;
        cst.weighty = 1.0;
        steps.add(step1000, cst);
        
        cst.gridx = 0;
        cst.gridy = 4;
        cst.weightx = 1.0;
        cst.weighty = 1.0;
        
        steps.add(reset, cst);
        
        JPanel panelright = new JPanel(new BorderLayout());
        
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);

        contents = getContentPane();
        
        JPanel fieldPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        
        fieldPanel.add(stepLabel, BorderLayout.NORTH);
        fieldPanel.add(fieldView, BorderLayout.CENTER);
        fieldPanel.add(population, BorderLayout.SOUTH);
        
        JPanel monitorWrap = new JPanel(new BorderLayout());
        monitorWrap.add(monitorView, BorderLayout.SOUTH);
        monitorWrap.setBackground(Color.white);
        
        // Add sliders to the settings frame
        Settings settings = new Settings();
        settings.add(creationChanceRabbit);
        settings.add(creationChanceFox);
        settings.add(creationChanceBear);
        settings.add(lifeTimeRabbit);
        settings.add(lifeTimeFox);
        settings.add(lifeTimeBear);
        settings.add(breedAgeRabbit);
        settings.add(breedAgeFox);
        settings.add(breedAgeBear);
        settings.add(breedProbabilityRabbit);
        settings.add(breedProbabilityFox);
        settings.add(breedProbabilityBear);
        settings.add(litterSizeRabbit);
        settings.add(litterSizeFox);
        settings.add(litterSizeBear);
        settings.add(foodValueFox);
        settings.add(foodValueBear);
        settings.add(creationChanceHunter);
        settings.add(bulletLimitHunter);
        settings.add(waitLimitHunter);
        settings.add(infectionChance);
        settings.add(stepsBeforeDeath);
        settings.add(maximumAmountHunters);
        
        // Add pages to tabbedpane
        tabbedPane.addTab("Field", fieldPanel);
        tabbedPane.addTab("Text", textView);
        tabbedPane.addTab("Graph", monitorWrap);
        
        panelright.setLayout(new BorderLayout());
        
        panelright.add(tabbedPane);
        
        panelleft.add(steps, BorderLayout.NORTH);
        panelleft.add(legend, BorderLayout.SOUTH);
        
        contents.add(panelright, BorderLayout.EAST);
        contents.add(panelleft, BorderLayout.WEST);
        
        
        pack();
        setVisible(true);
    }
    
    /**
     * Get the 1 step button
     * @return the 1 step button
     */
    public JButton getStep1Button() {
    	return this.step1;
    }
    
    /**
     * Get the 100 step button
     * @return the 100 step button
     */
    public JButton getStep100Button() {
    	return this.step100;
    }
    
    /**
     * Get the 1000 step button
     * @return the 1000 step button
     */
    public JButton getStep1000Button() {
    	return this.step1000;
    }
    
    /**
     * Disable the buttons
     */
    public void disableButtons() {
    	this.step1.setEnabled(false);
    	this.step100.setEnabled(false);
    	this.step1000.setEnabled(false);
    	this.reset.setEnabled(false);
    }
    
    /**
     * Enable the buttons
     */
    public void enableButtons() {
    	this.step1.setEnabled(true);
    	this.step100.setEnabled(true);
    	this.step1000.setEnabled(true);
    	this.reset.setEnabled(true);
    }
    
    /**
     * Return the current color of the actor
     * @return the color
     */
    public static Color getColor(Object animal) {
    	if(animal instanceof Bear || animal instanceof Fox || animal instanceof Rabbit) {
            Animal a = (Animal) animal;
            return a.getColor();
        } else if(animal instanceof Hunter) {
        	Hunter h = (Hunter) animal;
        	return h.getColor();
        }
		return null;
    }
    
    /**
     * Return the regular color of the actor
     * @return the color
     */
    public static Color getOfficialColor(Object animal) {
    	if(animal instanceof Bear || animal instanceof Fox || animal instanceof Rabbit) {
            Animal a = (Animal) animal;
            return a.getOfficialColor();
        } else if(animal instanceof Hunter) {
        	Hunter h = (Hunter) animal;
        	return h.getOfficialColor();
        }
		return null;
    }
    
    /**
     * Get the fieldstats
     * @return the fieldstats
     */
    public FieldStats getStats() {
    	return stats;
    }
    
    /**
     * Get the reset button
     * @return the reset button
     */
    public JButton getResetButton() {
    	return this.reset;
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
    
    /**
     * Getters for the values of the slider
     * @return the value
     */
    
    public static int getLifeTimeRabbit() {
		return lifeTimeRabbit.getValue();
	}
	public static int getLifeTimeFox() {
		return lifeTimeFox.getValue();
	}
	public static int getLifeTimeBear() {
		return lifeTimeBear.getValue();
	}
	
	public static int getBreedAgeRabbit() {
		   return breedAgeRabbit.getValue();
		}
	
	public static int getBreedAgeFox() {
		   return breedAgeFox.getValue();
		}
	public static int getBreedAgeBear() {
	   return breedAgeBear.getValue();
	}
	
	public static int getBreedProbabilityRabbit() {
		   return breedProbabilityRabbit.getValue();
		}
	
	public static int getBreedProbabilityFox() {
		   return breedProbabilityFox.getValue();
		}
	public static int getBreedProbabilityBear() {
	   return breedProbabilityBear.getValue();
	}
	public static int getLitterSizeRabbit() {
		   return litterSizeRabbit.getValue();
		}
	
	public static int getgetLitterSizeFox() {
		   return litterSizeFox.getValue();
		}
	public static int getgetLitterSizeBear() {
	   return litterSizeBear.getValue();
	}
	
	public static int getFoodValueFox() {
		   return foodValueFox.getValue();
		}
	public static int getFoodValueBear() {
	   return foodValueBear.getValue();
	}
	public static int getWaitLimitHunter() {
		return waitLimitHunter.getValue();
	}
	public static int getBulletLimitHunter() {
		return bulletLimitHunter.getValue();
	}
	public static int getInfectionChance() {
		return infectionChance.getValue();
	}
	public static int getStepsBeforeDeath() {
		return stepsBeforeDeath.getValue();
	}
	public static int getCreationChanceFox() {
		return creationChanceFox.getValue();
	}
	public static int getCreationChanceRabbit() {
		return creationChanceRabbit.getValue();
	}
	public static int getCreationChanceBear() {
		return creationChanceBear.getValue();
	}
	public static int getCreationChanceHunter() {
		return creationChanceHunter.getValue();
	}
	public static int getMaximumAmountHunters() {
		return maximumAmountHunters.getValue();
	}
}

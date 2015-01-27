import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

/**
 * The GraphView provides a view of two populations of actors in the field as a line graph
 * over time. In its current version, it can only plot exactly two different classes of 
 * animals. If further animals are introduced, they will not currently be displayed.
 * 
 * @author Michael K�lling and David J. Barnes
 * @version 2011.07.31
 */
public class GraphView
{
    private static final Color LIGHT_GRAY = new Color(0, 0, 0, 40);

    private static JFrame frame;
    private static GraphPanel graph;
    private static JLabel stepLabel;
    private static JLabel countLabel;

    // The classes being tracked by this view
    private Set<Class> classes;
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Constructor.
     * 
     * @param width The width of the plotter window (in pixles).
     * @param height The height of the plotter window (in pixles).
     * @param startMax The initial maximum value for the y axis.
     * @param world The world object.
     * @param class1 The first class to be plotted.
     * @param width The second class to be plotted.
     */
    public GraphView(int width, int height, int startMax)
    {
        stats = new FieldStats();
        classes = new HashSet<Class>();
        colors = new HashMap<Class, Color>();

        if (frame == null) {
            frame = makeFrame(width, height, startMax);
        }
        else {
            graph.newRun();
        }

        //showStatus(0, null);
    }

    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
        classes = colors.keySet();
    }

    /**
     * Show the current status of the field. The status is shown by displaying a line graph for
     * two classes in the field. This view currently does not work for more (or fewer) than exactly
     * two classes. If the field contains more than two different types of animal, only two of the classes
     * will be plotted.
     * 
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, java.util.List<Actor> actors, SimulatorView view)
    {
        graph.update(step, actors, view);
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
     * Prepare for a new run.
     */
    public void reset()
    {
        graph.newRun();
    }
    
    /**
     * Prepare the frame for the graph display.
     */
    private JFrame makeFrame(int width, int height, int startMax)
    {
        JFrame frame = new JFrame("Graph View");

        Container contentPane = frame.getContentPane();

        graph = new GraphPanel(width, height, startMax);
        contentPane.add(graph, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Step:"));
        stepLabel = new JLabel("");
        bottom.add(stepLabel);
        countLabel = new JLabel(" ");
        bottom.add(countLabel);
        contentPane.add(bottom, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setLocation(20, 600);

        frame.setVisible(true);

        return frame;
    }

    // ============================================================================
    /**
     * Nested class: a component to display the graph.
     */
    class GraphPanel extends JComponent
    {
        private static final double SCALE_FACTOR = 0.8;

        // An internal image buffer that is used for painting. For
        // actual display, this image buffer is then copied to screen.
        private BufferedImage graphImage;
        private HashMap<Integer, Integer> lastVal = new HashMap<Integer, Integer>();
        private int yMax;

        /**
         * Create a new, empty GraphPanel.
         */
        public GraphPanel(int width, int height, int startMax)
        {
            graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            clearImage();
            yMax = startMax;
        }

        /**
         * Indicate a new simulation run on this panel.
         */
        public void newRun()
        {
            int height = graphImage.getHeight();
            int width = graphImage.getWidth();

            Graphics g = graphImage.getGraphics();
            g.copyArea(0, 0, width, height, -1, 0);            
            g.setColor(Color.BLACK);
            g.drawLine(width-2, 0, width-2, height);
            g.drawLine(width-2, 0, width-2, height);
            this.repaint();
        }

        /**
         * Dispay a new point of data.
         */
        public void update(int step, java.util.List<Actor> actors, SimulatorView view)
        {
        	Slice[] slices = PieChartView.getSlices(actors, view);
        	
        	for(int i = 0; i < slices.length; i++) {
        		Graphics ga = graphImage.getGraphics();

                int height = graphImage.getHeight();
                int width = graphImage.getWidth();

                // move graph one pixel to left
                ga.copyArea(0, 0, width, height, -1, 0);

                // calculate y, check whether it's out of screen. scale down if necessary.
                int y = (int) (height - ((height * slices[i].getValue()) / yMax) - 1);
                while (y<0) {
                    scaleDown(slices);
                    y = (int) (height - ((height * slices[i].getValue()) / yMax) - 1);
                }
                ga.setColor(slices[i].getColor());
                if(lastVal.containsKey(i)) {
                	ga.setColor(LIGHT_GRAY);
                    ga.drawLine(width-2, y, width-2, height);
                    ga.setColor(slices[i].getColor());
                    ga.drawLine(width-6, lastVal.get(i), width-2, y);
                } else {
                	ga.drawLine(width-6, y, width-2, height);
                }

                lastVal.put(i, y);
                repaint();
        	}
        	
        }

        /**
         * Scale the current graph down vertically to make more room at the top.
         */
        public void scaleDown(Slice[] slices)
        {
            Graphics ga = graphImage.getGraphics();
            int height = graphImage.getHeight();
            int width = graphImage.getWidth();

            BufferedImage tmpImage = new BufferedImage(width, (int)(height*SCALE_FACTOR), 
                                                       BufferedImage.TYPE_INT_RGB);
            Graphics2D gtmp = (Graphics2D) tmpImage.getGraphics();

            gtmp.scale(1.0, SCALE_FACTOR);
            gtmp.drawImage(graphImage, 0, 0, null);

            int oldTop = (int) (height * (1.0-SCALE_FACTOR));

            ga.setColor(Color.WHITE);
            ga.fillRect(0, 0, width, oldTop);
            ga.drawImage(tmpImage, 0, oldTop, null);

            yMax = (int) (yMax / SCALE_FACTOR);
            for(int i = 0; i < slices.length; i++) {
            	if(lastVal.containsKey(i)) {
            		lastVal.put(i, oldTop + (int) (lastVal.get(i) * SCALE_FACTOR));
                } else {
                	lastVal.put(i, 0);
                }
            }
            this.repaint();
        }


        /**
         * Clear the image on this panel.
         */
        public void clearImage()
        {
            Graphics ga = graphImage.getGraphics();
            ga.setColor(Color.WHITE);
            ga.fillRect(0, 0, graphImage.getWidth(), graphImage.getHeight());
            repaint();
        }

        // The following methods are redefinitions of methods
        // inherited from superclasses.

        /**
         * Tell the layout manager how big we would like to be.
         * (This method gets called by layout managers for placing
         * the components.)
         * 
         * @return The preferred dimension for this component.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(graphImage.getWidth(), graphImage.getHeight());
        }

        /**
         * This component is opaque.
         */
        public boolean isOpaque()
        {
            return true;
        }

        /**
         * This component needs to be redisplayed. Copy the internal image 
         * to screen. (This method gets called by the Swing screen painter 
         * every time it want this component displayed.)
         * 
         * @param g The graphics context that can be used to draw on this component.
         */
        public void paintComponent(Graphics g)
        {
            Dimension size = getSize();
            //g.clearRect(0, 0, size.width, size.height);
            if(graphImage != null) {
                g.drawImage(graphImage, 0, 0, null);
            }
        }
    }
}

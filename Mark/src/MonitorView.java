import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

/**
 * A class to bundle the pie chart, graph chart and the bar graph into one single JPanel. 
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MonitorView extends JPanel {
	PieChart3 pieChart;
	GraphView graphChart;
	BarGraph barChart;
	
	/**
	 * The constructor to make a JPanel containing the charts.
	 */
	public MonitorView() {
		
		pieChart = new PieChart3();
		graphChart = new GraphView(350, 200);
		barChart = new BarGraph();
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		panel1.setPreferredSize(new Dimension(200, 200));
		panel2.setPreferredSize(new Dimension(350, 200));
		panel3.setPreferredSize(new Dimension(200, 200));
		
		
		panel1.setLayout(new BorderLayout());
		panel1.add(pieChart, BorderLayout.CENTER);
		panel2.setLayout(new BorderLayout());
		panel2.add(graphChart.getPanel(), BorderLayout.CENTER);
		panel3.setLayout(new BorderLayout());
		panel3.add(barChart, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(panel1,BorderLayout.WEST);
		add(panel2,BorderLayout.CENTER);
		add(panel3, BorderLayout.EAST);
		
		setVisible(true);
	}
	/**
	 * Update the charts with the values given in the parameters
	 * @param actors A list of all animals and hunters
	 * @param step The current step
	 */
	public void update(List<Actor> actors, int step) {
		
		Slice[] slices = getSlices(actors, true);
		
		pieChart.updateChart(slices);
        graphChart.showStatus(step, actors);
        barChart.update(slices);
        
        
	}
	/**
	 * 
	 * Make a bundle of the values from the actors.
	 * 
	 * @param actors A list of all the animals and hunters.
	 * @param official A boolean that indicates whether the official or the current color of the animal must be used.
	 * @return Slice
	 */
	public static Slice[] getSlices(List<Actor> actors, boolean official) {
		HashMap<String, ArrayList<Actor>> orderedList = new HashMap<String, ArrayList<Actor>>();
		ArrayList<String> reminder = new ArrayList<String>();
		
		for(Actor actor : actors) {
			String className = actor.getClass().toString();
			if(orderedList.containsKey(className)) {
				ArrayList<Actor> temp = orderedList.get(className);
				temp.add(actor);
				orderedList.put(className, temp);
			} else {
				ArrayList<Actor> temp = new ArrayList<Actor>();
				temp.add(actor);
				orderedList.put(className,temp);
				reminder.add(className);
			}
		}
		
		Slice[] slices = new Slice[reminder.size()];
		Collections.sort(reminder);
		for(int i = 0; i < reminder.size(); i++) {
			int count = orderedList.get(reminder.get(i)).size();
			if(official) {
				slices[i] = new Slice(count, SimulatorView.getOfficialColor(orderedList.get(reminder.get(i)).get(0)));
			} else {
				slices[i] = new Slice(count, SimulatorView.getColor(orderedList.get(reminder.get(i)).get(0)));
			}
		}
		return slices;
	}
}

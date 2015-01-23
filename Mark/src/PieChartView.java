import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;


public class PieChartView extends JFrame {
	private PieChart3 pieChart;
	
	public PieChartView() {
		setTitle("Fox and Rabbit Pie Chart");
		
		pieChart = new PieChart3();
		Container contents = getContentPane();
		pieChart.setPreferredSize(new Dimension(400, 400));
		
		contents.add(pieChart);
		pack();
        setVisible(true);
	}
	
	public void updatePieChart(List<Actor> actors, SimulatorView view) {
		
		Slice[] slices = PieChartView.getSlices(actors, view);
		
		pieChart.updateChart(slices);
	}
	
	public static Slice[] getSlices(List<Actor> actors, SimulatorView view) {
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
			slices[i] = new Slice(count, view.getColor(orderedList.get(reminder.get(i)).get(0).getClass()));
		}
		return slices;
	}
}

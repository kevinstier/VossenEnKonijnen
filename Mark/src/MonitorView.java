import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MonitorView extends JFrame {
	SimulatorView simView;
	PieChart3 pieChart;
	GraphView graphChart;
	BarGraph barChart;
	
	
	public MonitorView(SimulatorView view) {
		simView = view;
		setTitle("MonitorView");
		setResizable(false);
		
		pieChart = new PieChart3();
		graphChart = new GraphView(350, 200, view.getStats());
		barChart = new BarGraph(view);
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		panel1.setPreferredSize(new Dimension(200, 200));
		panel2.setPreferredSize(new Dimension(350, 200));
		panel3.setPreferredSize(new Dimension(200, 200));
		
		Container content = getContentPane();
		
		panel1.setLayout(new BorderLayout());
		panel1.add(pieChart, BorderLayout.CENTER);
		panel2.setLayout(new BorderLayout());
		panel2.add(graphChart.getPanel(), BorderLayout.CENTER);
		panel3.setLayout(new BorderLayout());
		panel3.add(barChart, BorderLayout.CENTER);
		
		content.setLayout(new BorderLayout());
		content.add(panel1,BorderLayout.WEST);
		content.add(panel2,BorderLayout.CENTER);
		content.add(panel3, BorderLayout.EAST);
		
		pack();
		setVisible(true);
	}
	
	public void update(List<Actor> actors, int step) {
		
		Slice[] slices = getSlices(actors, simView);
		
		pieChart.updateChart(slices);
        graphChart.showStatus(step, actors, simView);
        barChart.update(slices);
        
        
	}
	
	private Slice[] getSlices(List<Actor> actors, SimulatorView view) {
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
			slices[i] = new Slice(count, view.getColor(orderedList.get(reminder.get(i)).get(0)));
		}
		return slices;
	}
}

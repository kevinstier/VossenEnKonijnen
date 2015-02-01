import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
	public static void main(String [ ] args) {
		
		final Simulator simulator = new Simulator();
		
		JButton step1 = simulator.getSimulatorView().getStep1Button();
		JButton step100 = simulator.getSimulatorView().getStep100Button();
		JButton step1000 = simulator.getSimulatorView().getStep1000Button();
		JButton reset = simulator.getSimulatorView().getResetButton();
		
		step1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.simulateOneStep();
			}
		});
		step100.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				simulator.getSimulatorView().disableButtons();
				
				Thread moreStepsThread = new Thread( new Runnable() {
					public void run() {
						simulator.simulate(100);
						simulator.getSimulatorView().enableButtons();
					}
				});
				moreStepsThread.start();
			}
		});
		
		step1000.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				simulator.getSimulatorView().disableButtons();
				
				Thread moreStepsThread = new Thread( new Runnable() {
					public void run() {
						simulator.simulate(1000);
						simulator.getSimulatorView().enableButtons();
					}
				});
				moreStepsThread.start();
			}
		});
		
		reset.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.reset();
			}
		});
	}
}

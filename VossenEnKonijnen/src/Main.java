import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
	public static void main(String [ ] args) {
		
		final Simulator simulator = new Simulator();
		
		JButton oneStep = simulator.getSimulatorView().getOneStepButton();
		JButton moreSteps = simulator.getSimulatorView().getMoreStepsButton();
		JButton reset = simulator.getSimulatorView().getResetButton();
		
		oneStep.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.simulateOneStep();
			}
		});
		moreSteps.addActionListener(new ActionListener() {
			
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
		
		reset.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.reset();
			}
		});
	}
}

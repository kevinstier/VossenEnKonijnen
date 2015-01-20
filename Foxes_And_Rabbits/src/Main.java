import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String [ ] args) {
		
		final Simulator simulator = new Simulator();
		
		JButton oneStep = simulator.getSimulatorView().getOneStepButton();
		JButton moreSteps = simulator.getSimulatorView().getMoreStepsButton();
		
		oneStep.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.simulateOneStep();
			}
		});
		moreSteps.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				Thread moreStepsThread = new Thread( new Runnable() {
					public void run() {
						simulator.simulate(100);
					}
				});
				moreStepsThread.start();
			}
		});
	}
}

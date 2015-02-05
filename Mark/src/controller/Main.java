package controller;

import model.*;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main class to execute the simulation
 * 
 * @author Mark Nijboer
 * @version 2015.02.3
 */
public class Main {
	/**
	 * The main method
	 * 
	 * @param args
	 */
	public static void main(String [ ] args) {
		
		final Simulator simulator = new Simulator();
		
		JButton step1 = simulator.getSimulatorView().getStep1Button();
		JButton step100 = simulator.getSimulatorView().getStep100Button();
		JButton step1000 = simulator.getSimulatorView().getStep1000Button();
		JButton reset = simulator.getSimulatorView().getResetButton();
		JButton rabbitIllness = simulator.getSimulatorView().getRabbitIllnessButton();
		JButton rabbitIllness10 = simulator.getSimulatorView().getRabbitIllnessButton10();
		
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
						if(simulator.simulate(100)) {
							simulator.getSimulatorView().enableButtons();
						}
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
						if(simulator.simulate(1000)) {
							simulator.getSimulatorView().enableButtons();
						}
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
		
		rabbitIllness.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.makeRandomRabbitIll(1);
			}
		});
		
		rabbitIllness10.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				simulator.makeRandomRabbitIll(10);
			}
		});
		
	}
}

package assign09;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

/**
 * This class represents the main window for the Lights Out game.
 * It manages the 5x5 grid of lights, the control buttons, and the overall game logic.
 * 
 * @author Sophia Schluth
 * @version 11/13/2025
 */
public class LightsOutFrame extends JFrame implements ActionListener {

	private LightsOutButton[][] grid;
	private JButton randomlySet;
	private JButton manuallySet;
	private boolean inManualSetup;
	
	/**
     * Constructs a new LightsOutFrame, initializes the 5x5 game grid,
     * sets up the control buttons, installs all listeners, and generates
     * an initial randomized puzzle configuration.
	 */
	public LightsOutFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		grid = new LightsOutButton[5][5];
		randomlySet = new JButton("Randomize");
		manuallySet = new JButton("Enter Manual Setup");
		inManualSetup = false;
		
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(5, 5));

		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				LightsOutButton button = new LightsOutButton(r, c);
				button.addActionListener(this);
				button.setMargin(new Insets(0,0,0,0));
				button.setBorderPainted(false);
				button.setContentAreaFilled(false);
				gridPanel.add(button);
				grid[r][c] = button;
			}
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		randomlySet.addActionListener(this);
		manuallySet.addActionListener(this);
		
		buttonPanel.add(randomlySet);
		buttonPanel.add(manuallySet);
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BorderLayout());
		containerPanel.add(gridPanel, BorderLayout.CENTER);
		containerPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setContentPane(containerPanel);
		randomize();
	}
	
	/**
     * Randomizes the board by performing ten random toggles. Each toggle
     * affects the selected light and its neighbors, ensuring the board
     * begins in a solvable but unpredictable configuration.
	 */
	public void randomize() {
		Random ran = new Random();
		
		for (int i = 0; i < 10; i++ ) {
			int ranRow = ran.nextInt(5);
			int ranCol = ran.nextInt(5);
			
			grid[ranRow][ranCol].toggle();
			toggleNeighbors(ranRow, ranCol);
		}
	}
	
	/**
     * Returns whether the light at the specified location is currently on.
     *
     * @param row - row index (0–4)
     * @param column - column index (0–4)
     * @return true if the specified light is on; false otherwise
     * @throws IndexOutOfBoundsException if row or column is outside 0–4
	 */
	public boolean lightIsOn(int row, int column) {
		if (row < 0 || row > 4 || column < 0 || column > 4) {
			throw new IndexOutOfBoundsException("Row/column must be between 0 and 4");
		}
		
		return grid[row][column].isOn();
	}
	
	
	/**
     * Toggles the light at the given position and automatically toggles its
     * orthogonal neighbors (up, down, left, right) if they exist.
     *
     * @param row - row index (0–4)
     * @param column - column index (0–4)
     * @throws IndexOutOfBoundsException if row or column is outside 0–4
	 */
	public void toggleLight(int row, int column) {
		if (row < 0 || row > 4 || column < 0 || column > 4) {
			throw new IndexOutOfBoundsException("Row/column must be between 0 and 4");
		}
		
		grid[row][column].toggle();
		toggleNeighbors(row, column);
	}
	
	/**
	 * Handles all user interaction with the board and control panels
	 * 	- if LightsOutButton is clicked, it toggles that light and the neighboring lights
	 * 		unless in manual mode.
	 * 	- if the "Randomize" button is clicked, the board is randomized.
	 * 	- if the Enter/Exit Manual Setup is clicked, manual setup mode is toggled on or off.
	 * 
	 * @param e - event triggered by button click
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof LightsOutButton) {
			LightsOutButton clicked = (LightsOutButton) e.getSource();
			clicked.toggle();
			
			if (!inManualSetup) {
				toggleNeighbors(clicked.getRow(), clicked.getColumn());
				checkIfSolved();
			}
		}
		
		else if (e.getSource() == randomlySet) 
			randomize();
		
		else if (e.getSource() == manuallySet) {
			if (!inManualSetup) {
				manuallySet.setText("Exit Manual Setup");
				inManualSetup = true;
			}
			else {
				manuallySet.setText("Enter Manual Setup");
				inManualSetup = false;
			}
		}
	}	
	
	/**
	 * Toggles the north, east, south, and west neighbors of the given grid position.
	 * Only valid neighbors within bounds are toggled.
	 * 
	 * @param r - row index
	 * @param c - column index
	 */
	private void toggleNeighbors(int r, int c) {
		if (r < 4)
			grid[r + 1][c].toggle();
		if (r > 0) 
			grid[r - 1][c].toggle();
		if (c < 4)
			grid[r][c + 1].toggle();
		if (c > 0)
			grid[r][c - 1].toggle();
	}
	
	/**
	 * Checks whether all lights on the board are off.
	 * If all lights are off, congratulations message pops up.
	 */
	private void checkIfSolved() {
		boolean solved = true;
		
		for (LightsOutButton[] row : grid) {
			for(LightsOutButton light : row) {
				if (light.isOn()) 
					solved = false;
			}
		}
		
		if (solved) {
			JOptionPane.showMessageDialog(this, "You solved the puzzle!");
			
		}
	}
}

package assign09;

import javax.swing.*;

/**
 * This class represents a button in a 5x5 Lights Out grid that tracks whether
 * a light is on or off.
 * 
 * @author Sophia Schluth
 * @version 11/13/2025
 */
public class LightsOutButton extends JButton{
	
	private int row;
	private int col;
	private boolean isOn;
	private ImageIcon lightsOnIcon;
	private ImageIcon lightsOffIcon;
	
	/**
	 * Constructs a light button with given row and column values.
	 * 
	 * @param row - row value
	 * @param col - column value
	 */
	public LightsOutButton (int row, int col) {
		super();
		this.row = row;
		this.col = col;
		isOn = false;

		lightsOnIcon  = new ImageIcon(getClass().getResource("light_on.png"));
		lightsOffIcon = new ImageIcon(getClass().getResource("light_off.png"));
		setIcon(lightsOffIcon);
	}
	
	/**
	 * Changes the light status to on, if previously off
	 * and changes the light status to off, if previously on.
	 */
	public void toggle() {
		if (isOn) {
			isOn = false;
			setIcon(lightsOffIcon);
		}
		else {
			isOn = true;
			setIcon(lightsOnIcon);
		}
	}
	
	/**
	 * Returns the row value for this light button.
	 * 
	 * @return row value
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column value for this light button
	 * 
	 * @return column value
	 */
	public int getColumn() {
		return col;
	}
	
	/**
	 * Returns true if this light button is on, and false otherwise.
	 * 
	 * @return boolean value based on the status of this light
	 */
	public boolean isOn() {
		return isOn;
	}
}
	

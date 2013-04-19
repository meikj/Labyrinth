package com.labyrinth.ui.interfaces;

import com.labyrinth.Labyrinth;
import com.labyrinth.game.SettingsManager;
import com.labyrinth.ui.CharacterElements;

/**
 * Represents the options screen user interface.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.19042013
 *
 */
public class OptionsUI extends UserInterface {
	
	private SettingsManager settings;
	
	/**
	 * Construct an options interface.
	 */
	public OptionsUI(SettingsManager settings) {
		super("OPTIONS");
		this.settings = settings;
	}
	
	/**
	 * Parse the option to manipulate.
	 * 
	 * Supported options:
	 * 
	 * 		ROWS <no>
	 * 		COLUMNS <no>
	 * 		LEADERBOARD <path>
	 * 
	 * The command 'apply' saves the changes.
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		super.parse(args);
		
		if(args[0].toLowerCase().equals("rows")) {
			if(args.length != 2)
				throw new IllegalArgumentException("Usage: rows <no>");
			
			// Alter rows
			int rows; 
			
			try {
				rows = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("Please enter a valid integer between 5 and 15 (inclusive).");
			}
			
			if(rows < 5 || rows > 15)
				throw new IllegalArgumentException("Please enter a valid integer between 5 and 15 (inclusive).");
			
			settings.setRows(rows);
		} else if(args[0].toLowerCase().equals("columns")) {
			if(args.length != 2)
				throw new IllegalArgumentException("Usage: columns <no>");
			
			// Alter columns
			int columns; 
			
			try {
				columns = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("Please enter a valid integer between 5 and 15 (inclusive).");
			}
			
			if(columns < 5 || columns > 15)
				throw new IllegalArgumentException("Please enter a valid integer between 5 and 15 (inclusive).");
			
			settings.setColumns(columns);
		} else if(args[0].toLowerCase().equals("leaderboard")) {
			if(args.length != 2)
				throw new IllegalArgumentException("Usage: leaderboard <path>");
			
			settings.setLeaderboard(args[1]);
		} else if(args[0].toLowerCase().equals("apply")) {
			System.out.print("Applying settings to " + Labyrinth.SETTINGS_FILE + "... ");
			
			try {
				settings.save(Labyrinth.SETTINGS_FILE);
			} catch(Exception e) {
				System.out.println("failed!");
				System.out.println();
			}
			
			System.out.println("success!");
			System.out.println();
		}
	}
	
	/**
	 * Prompt the user for their option.
	 */
	public String[] prompt() {
		System.out.print("\n    Option: ");
		return super.prompt();
	}
	
	/**
	 * Initialise the options window.
	 */
	protected void makeWindow() {
		super.makeWindow();
		
		addContent("To alter an option, type <option_name> <new_value>. Current options:");
		addContent("");
		
		// Add the options to the window
		addContent("    * ROWS         " + settings.getRows());
		addContent("    * COLUMNS      " + settings.getColumns());
		addContent("    * LEADERBOARD  " + settings.getLeaderboard());
		
		addContent("");
		addContent("DETAILS");
		addContent("------------------------------------------------------------------------");
		addContent("");
		addContent("    * VERSION      Labyrinth v0.1.19042013                              ");
		addContent("    * CHARSET      " + CharacterElements.charSet);
		addContent("");
		addContent("------------------------------------------------------------------------");
		addContent("Enter 'back' to go back                   Enter 'apply' to apply changes");
	}

}

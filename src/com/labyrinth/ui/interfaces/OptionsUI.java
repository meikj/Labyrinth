package com.labyrinth.ui.interfaces;

import java.util.LinkedList;

import com.labyrinth.game.Labyrinth;
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
public class OptionsUI extends UI {
	
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
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		super.parse(args);
		
		// TODO: Implement the options
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
		LinkedList<String> optionsFile = null;
		
		addContent("To alter an option, type <option_name> <new_value>. Current options:");
		addContent("");
		
		try {
			optionsFile = processFile(Labyrinth.SETTINGS_FILE);
		} catch(Exception e) {
			addContent("    No options available");
		}
		
		// Add the available saved games to the window
		if(optionsFile == null) {
			addContent("    No options available");
		} else {
			for(String line : optionsFile) {
				addContent("    * " + line);
			}
		}
		
		addContent("");
		addContent("Labyrinth v0.1.18042013  Charset: " + CharacterElements.charSet);
		addContent("");
		contents.add("------------------------------------------------------------------------");
		addContent("Enter 'back' to go back, or enter 'apply' to apply changes.");
	}

}

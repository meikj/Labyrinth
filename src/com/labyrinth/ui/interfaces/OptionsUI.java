package com.labyrinth.ui.interfaces;

import java.util.LinkedList;

import com.labyrinth.game.Labyrinth;
import com.labyrinth.ui.CharacterElements;

public class OptionsUI extends UI {
	
	public OptionsUI() {
		super("OPTIONS");
	}

	public void parse(String[] args) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Initialise the options window.
	 */
	protected void makeWindow() {
		LinkedList<String> optionsFile = null;
		
		addContent("To alter an option, type <option_name> <new_value>. Current options:");
		addContent("");
		
		try {
			optionsFile = processFile(Labyrinth.SETTINGS_PATH);
		} catch(Exception e) {
			addContent("    No options available");
		}
		
		// Add the available saved games to the window
		if(optionsFile.isEmpty()) {
			addContent("    No options available");
		} else {
			for(String line : optionsFile) {
				addContent("    * " + line);
			}
		}
		
		addContent("");
		addContent("Labyrinth v0.1.18042013    Charset: " + CharacterElements.charSet);
	}

}

package com.labyrinth;

import com.labyrinth.ui.*;
import com.labyrinth.ui.interfaces.MainMenuUI;

/**
 * The main entry point to the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.22032013
 *
 */
public class Labyrinth {
	
	// Default paths
	public static final String SETTINGS_FILE = "settings/default";
	public static final String HELP_FILE = "media/help.txt";
	public static final String LOAD_PATH = "saves/";
	
	public static void main(String[] args) {
		// Set the static characters in accordance to the charset in use
		CharacterElements.initCharacters();
		
		// Run the main menu
		MainMenuUI mainMenu = new MainMenuUI();
		mainMenu.run();
	}

}

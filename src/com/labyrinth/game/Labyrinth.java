package com.labyrinth.game;

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
	public static final String SETTINGS_PATH = System.getProperty("user.dir") + "/settings/default";
	public static final String HELP_PATH = System.getProperty("user.dir") + "/media/help.txt";
	public static final String LOAD_PATH = System.getProperty("user.dir") + "/saves/list";
	
	public static void main(String[] args) {
		CharacterElements.initCharacters(); // Set the static characters in accordance to the charset in use
		
		// Run the main menu
		MainMenuUI mainMenu = new MainMenuUI();
		mainMenu.run();
	}

}

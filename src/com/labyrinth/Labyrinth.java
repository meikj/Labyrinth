package com.labyrinth;

import com.labyrinth.ui.*;

/**
 * The main entry point to the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.22032013
 *
 */
public class Labyrinth {
	
	public static final String SETTINGS_PATH = System.getProperty("user.dir") + "/settings/settings.txt";
	
	public static void main(String[] args) {
		CharacterElements.initCharacters(); // Set the static characters in accordance to the charset in use
		SettingsManager settings = new SettingsManager(SETTINGS_PATH);
		
		// By default just start up a new Treasure Chase game
		//GameMode game = new TreasureChase(settings);
		//UserInterface ui = new UserInterface(game);
		//ui.runMenu();
	}

}

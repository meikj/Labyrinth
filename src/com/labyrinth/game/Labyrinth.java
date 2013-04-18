package com.labyrinth.game;

import com.labyrinth.game.modes.GameMode;
import com.labyrinth.game.modes.TreasureChase;
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
	
	// Default paths
	public static final String SETTINGS_PATH = System.getProperty("user.dir") + "/settings/default";
	public static final String HELP_PATH = System.getProperty("user.dir") + "/media/help.txt";
	public static final String LOAD_PATH = System.getProperty("user.dir") + "/saves/list";
	
	public static void main(String[] args) {
		CharacterElements.initCharacters(); // Set the static characters in accordance to the charset in use
		SettingsManager settings = new SettingsManager(SETTINGS_PATH);
		
		// By default just start up a new Treasure Chase game
		GameMode game = new TreasureChase(settings);
		UserInterface ui = new UserInterface(game);
		ui.runMenu();
	}

}

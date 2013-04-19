package com.labyrinth.ui.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import com.labyrinth.game.GameManager;
import com.labyrinth.game.Labyrinth;
import com.labyrinth.game.modes.GameMode;

/**
 * Represents the load game screen user interface.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.19042013
 *
 */
public class LoadUI extends UI {
	
	private GameMode game;
	private GameManager manager;
	private GameUI gameUI;
	
	/**
	 * Construct a load game interface.
	 */
	public LoadUI() {
		super("LOAD GAME");
	}
	
	/**
	 * Parse the name of the game to load.
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		GameMode newGame = null;
		
		// Attempt to load the save game
		try {
			newGame = manager.load(System.getProperty("user.dir") + "/saves/" + args[0] + ".txt");
		} catch(FileNotFoundException e) {
			throw new IllegalArgumentException("Couldn't find: " + System.getProperty("user.dir") + "/saves/" + args[0] + ".txt");
		} catch(IOException e) {
			throw new IllegalArgumentException("Error parsing: " + System.getProperty("user.dir") + "/saves/" + args[0] + ".txt");
		}
		
		// Assign the saved game to the active game and start the game ;)
		game = newGame;
		gameUI = new GameUI(game);
		gameUI.run();
	}
	
	/**
	 * Display the load screen and prompt the user for their option.
	 */
	public String[] prompt() {
		System.out.print("\n    Load Game: ");
		return super.prompt();
	}
	
	/**
	 * Initialise the load game window.
	 */
	protected void makeWindow() {
		LinkedList<String> loadFile = null;
		
		addContent("Available saved games to choose from:");
		addContent("");
		
		try {
			loadFile = processFile(Labyrinth.LOAD_PATH);
		} catch(Exception e) {
			addContent("    No saved games available");
		}
		
		// Add the available saved games to the window
		if(loadFile.isEmpty()) {
			addContent("    No saved games available");
		} else {
			for(String line : loadFile) {
				addContent("    * " + line);
			}
		}
	}

}

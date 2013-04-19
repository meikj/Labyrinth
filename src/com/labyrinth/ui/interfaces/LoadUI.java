package com.labyrinth.ui.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import com.labyrinth.Labyrinth;
import com.labyrinth.game.GameManager;
import com.labyrinth.game.modes.GameMode;
import com.labyrinth.ui.GameView;

/**
 * Represents the load game screen user interface.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.19042013
 *
 */
public class LoadUI extends UserInterface {
	
	private GameManager manager;
	private GameView gameUI;
	
	/**
	 * Construct a load game interface.
	 */
	public LoadUI() {
		super("LOAD GAME");
		manager = new GameManager();
	}
	
	/**
	 * Parse the name of the game to load.
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		super.parse(args);
		
		if(isRunning()) {
			GameMode newGame = null;
			
			// Attempt to load the save game
			try {
				newGame = manager.load(Labyrinth.LOAD_PATH + args[0]);
			} catch(FileNotFoundException e) {
				throw new IllegalArgumentException("Couldn't find: " + Labyrinth.LOAD_PATH + args[0]);
			} catch(IOException e) {
				throw new IllegalArgumentException("Error parsing: " + Labyrinth.LOAD_PATH + args[0]);
			}
			
			// Assign the saved game to the active game and start the game ;)
			if(newGame != null) {
				gameUI = new GameView(newGame);
				gameUI.run();
			} else {
				throw new IllegalArgumentException("Error parsing: " + Labyrinth.LOAD_PATH + args[0]);
			}
		}
	}
	
	/**
	 * Prompt the user for their option.
	 */
	public String[] prompt() {
		System.out.print("\n    Load Game: ");
		return super.prompt();
	}
	
	/**
	 * Initialise the load game window.
	 */
	protected void makeWindow() {
		super.makeWindow();
		
		LinkedList<String> loadFile = null;
		
		addContent("Available saved games to choose from:");
		addContent("");
		
		try {
			loadFile = processFile(Labyrinth.LOAD_PATH + "list");
		} catch(Exception e) {
			addContent("    No saved games available");
			return;
		}
		
		// Add the available saved games to the window
		if(loadFile == null) {
			addContent("    No saved games available");
		} else {
			for(String line : loadFile) {
				addContent("    * " + line);
			}
		}
		
		addContent("");
		addContent("------------------------------------------------------------------------");
		addContent("Enter 'back' to go back                                                 ");
	}

}

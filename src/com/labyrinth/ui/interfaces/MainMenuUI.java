package com.labyrinth.ui.interfaces;

import java.util.LinkedList;

import com.labyrinth.game.Labyrinth;
import com.labyrinth.game.SettingsManager;
import com.labyrinth.game.modes.TreasureChase;
import com.labyrinth.ui.Window;

public class MainMenuUI extends UI {
	
	private SettingsManager settings;
	
	// UI's
	GameUI gameUI;
	LoadUI loadUI;
	OptionsUI optionsUI;
	
	// Windows
	Window helpWindow;
	
	public MainMenuUI() {
		super();
		
		// Load in settings
		settings = new SettingsManager(Labyrinth.SETTINGS_PATH);
		
		// Set up UI's
		gameUI = new GameUI(new TreasureChase(settings));
		loadUI = new LoadUI();
		optionsUI = new OptionsUI();
		
		// Set up windows
		makeHelpWindow();
	}
	
	/**
	 * Prompt the user for their menu option.
	 */
	public String[] prompt() {
		System.out.print("\n    Option: ");
		return super.prompt();
	}
	
	/**
	 * Parse a menu option.
	 * 
	 * 1 - Play game
	 * 2 - Load game
	 * 3 - Options
	 * 4 - Help
	 * 5 - Exit
	 * 
	 * @param args The input in a tokenized form.
	 * @throws IllegalArgumentException When an invalid menu option is passed.
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		switch(Integer.parseInt(args[0])) {
		case 1:
			// Play game
			System.out.println("\nStarting new Treasure Chase game...\n");
			gameUI.run();
			break;
		case 2:
			// Load game
			System.out.println();
			loadUI.run();
			break;
		case 3:
			// Options
			System.out.println();
			optionsUI.run();
			break;
		case 4:
			// Help
			System.out.println();
			displayHelp();
			break;
		case 5:
			// Exit
			stop();
			break;
		default:
			throw new IllegalArgumentException("Please enter an option between 1-5 (inclusive).");
		}
	}
	
	/**
	 * Display the help file.
	 */
	public void displayHelp() {
		helpWindow.display();
		enterPrompt();
	}
	
	/**
	 * Initialise the main menu window.
	 */
	protected void makeWindow() {
		setLayoutPath("media/window_menu");
		
		addContent("1. Play game");
		addContent("2. Load game");
		addContent("3. Options");
		addContent("4. Help");
		addContent("5. Quit");
		
		try {
			refresh();
		} catch (Exception e) {
			System.out.println("Error: MainMenuUI.makeWindow(): Couldn't process layout file.");
		}
	}
	
	/**
	 * Initialise the help window.
	 */
	private void makeHelpWindow() {
		LinkedList<String> helpFile = null;
		helpWindow = new Window("HELP");
		
		try {
			helpFile = processFile(Labyrinth.HELP_PATH);
		} catch(Exception e) {
			helpWindow.addContent("Help file not available, sorry!");
		}
		
		// Add the help file to the window
		for(String line : helpFile) {
			helpWindow.addContent(line);
		}
	}

}

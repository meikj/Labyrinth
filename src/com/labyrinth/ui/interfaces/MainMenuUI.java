package com.labyrinth.ui.interfaces;

import java.util.LinkedList;

import com.labyrinth.Labyrinth;
import com.labyrinth.game.SettingsManager;
import com.labyrinth.game.modes.TreasureChase;
import com.labyrinth.ui.GameView;
import com.labyrinth.ui.Window;

/**
 * Represents the main menu user interface.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.19042013
 *
 */
public class MainMenuUI extends UserInterface {
	
	private SettingsManager settings;
	
	// UI's
	private GameView gameUI;
	private LoadUI loadUI;
	private OptionsUI optionsUI;
	
	// Windows (I don't think these warrant a full UserInterface given what they do)
	private Window helpWindow;
	
	/**
	 * Construct a main menu interface.
	 */
	public MainMenuUI() {
		super();
		
		// Load in settings
		settings = new SettingsManager(Labyrinth.SETTINGS_FILE);
		
		// Set up UI's
		gameUI = new GameView(new TreasureChase(settings));
		loadUI = new LoadUI();
		optionsUI = new OptionsUI(settings);
		
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
			gameUI = new GameView(new TreasureChase(settings));
			gameUI.run();
			break;
		case 2:
			// Load game
			System.out.println();
			loadUI.run();
			System.out.println();
			break;
		case 3:
			// Options
			System.out.println();
			optionsUI.run();
			System.out.println();
			
			// Reload settings
			try {
				settings.load(Labyrinth.SETTINGS_FILE);
			} catch(Exception e) {
				System.out.println("There was a problem reloading the settings!");
			}
			
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
	 * Initialise the main menu window with custom layout and content.
	 */
	protected void makeWindow() {
		super.makeWindow();
		
		setLayoutFile("media/window_menu");
		
		addContent("1. Play game");
		addContent("2. Load game");
		addContent("3. Options");
		addContent("4. Help");
		addContent("5. Exit");
		
		try {
			refresh();
		} catch (Exception e) {
			System.out.println("Error: MainMenuUI.makeWindow(): Couldn't process layout file: " + getLayoutFile());
		}
	}
	
	/**
	 * Initialise the help window.
	 */
	private void makeHelpWindow() {
		LinkedList<String> helpFile = null;
		helpWindow = new Window("HELP");
		
		try {
			helpFile = processFile(Labyrinth.HELP_FILE);
		} catch(Exception e) {
			helpWindow.addContent("Help file not available, sorry!");
		}
		
		// Add the help file to the window
		for(String line : helpFile) {
			helpWindow.addContent(line);
		}
	}

}

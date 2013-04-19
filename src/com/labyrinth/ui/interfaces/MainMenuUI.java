package com.labyrinth.ui.interfaces;

import java.util.LinkedList;

import com.labyrinth.game.Labyrinth;
import com.labyrinth.ui.CharacterElements;
import com.labyrinth.ui.Window;

public class MainMenuUI extends UI {
	
	// Windows
	Window menuWindow;
	Window loadWindow;
	Window optionsWindow;
	Window helpWindow;
	
	public MainMenuUI() {
		super("Main Menu");
		
		// Set up windows
		makeMenuWindow();
		makeLoadWindow();
		makeOptionsWindow();
		makeHelpWindow();
	}
	
	/**
	 * Display and run the main menu.
	 */
	public void run() {
		super.run();
		
		while(isRunning()) {
			displayMenu();
			
			System.out.print("\n    Option: ");
			String[] input = prompt();
			
			try {
				parse(input);
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
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
			run();
			break;
		case 2:
			// Load game
			System.out.println();
			loadWindow.display();
			break;
		case 3:
			// Options
			System.out.println();
			displayOptions();
			break;
		case 4:
			// Help
			System.out.println();
			displayHelp();
			break;
		case 5:
			// Exit
			stop();
		default:
			throw new IllegalArgumentException("Please enter an option between 1-5 (inclusive).");
		}
	}
	
	/**
	 * Display the menu.
	 */
	public void displayMenu() {
		menuWindow.display();
	}
	
	/**
	 * Display the options window.
	 */
	public void displayOptions() {
		optionsWindow.display();
		enterPrompt();
	}
	
	/**
	 * Display the help file.
	 */
	public void displayHelp() {
		helpWindow.display();
		enterPrompt();
	}
	
	/**
	 * Display and wait for enter. Useful to pause states until user is ready.
	 */
	public void enterPrompt() {
		System.out.print("\nPlease press [ENTER] to continue... ");
		
		input.nextLine();
	}
	
	/**
	 * Initialise the main menu window.
	 */
	private void makeMenuWindow() {
		menuWindow = new Window();
		menuWindow.setLayoutPath("media/window_menu");
		
		menuWindow.addContent("1. Play game");
		menuWindow.addContent("2. Load game");
		menuWindow.addContent("3. Options");
		menuWindow.addContent("4. Help");
		menuWindow.addContent("5. Quit");
		
		try {
			menuWindow.refresh();
		} catch (Exception e) {
			System.out.println("Error: makeMenuWindow(): Couldn't process layout file.");
		}
	}
	
	/**
	 * Initialise the load game window.
	 */
	private void makeLoadWindow() {
		LinkedList<String> loadFile = null;
		loadWindow = new Window("LOAD GAME");
		
		loadWindow.addContent("Available saved games to choose from:");
		loadWindow.addContent("");
		
		try {
			loadFile = processFile(Labyrinth.LOAD_PATH);
		} catch(Exception e) {
			loadWindow.addContent("    No saved games available");
		}
		
		// Add the available saved games to the window
		if(loadFile.isEmpty()) {
			loadWindow.addContent("    No saved games available");
		} else {
			for(String line : loadFile) {
				loadWindow.addContent("    * " + line);
			}
		}
	}
	
	/**
	 * Initialise the options window.
	 */
	private void makeOptionsWindow() {
		LinkedList<String> optionsFile = null;
		optionsWindow = new Window("OPTIONS");
		
		optionsWindow.addContent("To alter an option, type <option_name> <new_value>. Current options:");
		optionsWindow.addContent("");
		
		try {
			optionsFile = processFile(Labyrinth.SETTINGS_PATH);
		} catch(Exception e) {
			optionsWindow.addContent("    No options available");
		}
		
		// Add the available saved games to the window
		if(optionsFile.isEmpty()) {
			optionsWindow.addContent("    No options available");
		} else {
			for(String line : optionsFile) {
				optionsWindow.addContent("    * " + line);
			}
		}
		
		optionsWindow.addContent("");
		optionsWindow.addContent("Labyrinth v0.1.18042013    Charset: " + CharacterElements.charSet);
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

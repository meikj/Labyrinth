package com.labyrinth.ui.interfaces;

import java.util.LinkedList;

import com.labyrinth.game.Labyrinth;
import com.labyrinth.ui.CharacterElements;
import com.labyrinth.ui.Window;

public class MainMenuUI extends UI {
	
	// UI's
	Window menuWindow;
	LoadUI loadUI;
	Window optionsWindow;
	Window helpWindow;
	
	public MainMenuUI() {
		super("Main Menu");
		
		// Set up windows
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
			loadUI.run();
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
	 * Initialise the main menu window.
	 */
	protected void makeWindow() {
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

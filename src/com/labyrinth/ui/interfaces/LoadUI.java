package com.labyrinth.ui.interfaces;

import com.labyrinth.game.GameManager;
import com.labyrinth.game.modes.GameMode;

public class LoadUI extends UI {
	
	private GameMode game;
	private GameManager manager;

	public void parse(String[] args) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Display the load screen and prompt the user for their option.
	 */
	public void prompt() {
		String option;
		
		while() {
			displayLoad();
			System.out.print("\n    Load Game: ");
			
			try {
				option = input.nextLine();
			} catch(Exception e) {
				System.out.println("Please enter a valid game name.");
				enterPrompt();
				continue;
			}
		
			// Process option
			GameMode newGame = null;
			
			try {
				newGame = manager.load(System.getProperty("user.dir") + "/saves/" + option + ".txt");
			} catch(FileNotFoundException e) {
				System.out.println("Couldn't find: " + System.getProperty("user.dir") + "/saves/" + option + ".txt");
				enterPrompt();
				continue;
			} catch(IOException e) {
				System.out.println("Error parsing: " + System.getProperty("user.dir") + "/saves/" + option + ".txt");
				System.out.println(e.getMessage());
				e.printStackTrace();
				enterPrompt();
				continue;
			}
			
			// Assign the saved game to the active game
			if(newGame != null) {
				this.game = newGame;
				run();
			} else {
				System.out.println("Something went horribly wrong processing the save. Try again?");
				enterPrompt();
			}
		}
	}
	
	/**
	 * Display the load screen.
	 */
	public void displayLoad() {
		window.display();
	}

}

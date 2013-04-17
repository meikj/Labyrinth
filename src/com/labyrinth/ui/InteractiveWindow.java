package com.labyrinth.ui;

import java.util.Scanner;

/**
 * Represents a window which is interactable by the user through prompting
 * for input and text-based commands.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @Version 0.1.17042013
 *
 */
public abstract class InteractiveWindow extends Window {
	
	/**
	 * Prompt the user for input.
	 * 
	 * @return Returns the string the user entered.
	 */
	public String prompt() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\n> ");
		String input = scanner.nextLine();
		scanner.close();
		
		return input;
	}
	
	/**
	 * Parse a particular string of input.
	 * 
	 * @param input The input to parse.
	 */
	public abstract void parse(String input);
	
}

package com.labyrinth.ui.interfaces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import com.labyrinth.ui.Window;

/**
 * Represents the outline of a text-based UI for a general purpose. It provides 
 * functionality for prompting the user for input via the standard input stream. 
 * This input is then returned in a 'tokenized' form. For example:
 * 
 * 		input: "hello world" -> output: ["hello", "world"]
 * 
 * It also provides basic functionality for parsing the input.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.19042013
 *
 */
public abstract class UserInterface extends Window {

	private boolean running;
	protected Scanner input;
	
	/**
	 * Construct a basic user interface.
	 */
	public UserInterface() {
		super();
		
		running = false;
		input = new Scanner(System.in);
	}
	
	/**
	 * Construct a basic user interface with title.
	 * 
	 * @param title The title of the interface.
	 */
	public UserInterface(String title) {
		super(title);
		
		running = false;
		input = new Scanner(System.in);
	}
	
	/**
	 * Check whether or not the user interface is running.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Run the user interface until told to stop().
	 */
	public void run() {
		running = true;
		
		while(isRunning()) {
			// Redraw the window in case of new data
			makeWindow();
			
			display();
			String[] choice = prompt();
			
			try {
				parse(choice);
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}
	
	/**
	 * Stop the user interface.
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * Prompt the standard input for input.
	 * 
	 * @return The input in a tokenized array form.
	 */
	public String[] prompt() {
		String in = input.nextLine();
		return in.split(" ");
	}
	
	/**
	 * Parse a tokenized form of arguments (e.g. ["command", "arg1", ...]).
	 * 
	 * @param args The arguments to be parsed.
	 * @throws IllegalArgumentException Thrown when an invalid argument (or arguments) is passed.
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		if(args[0].toLowerCase().equals("back") || args[0].toLowerCase().equals("exit"))
			stop();
	}
	
	/**
	 * Initialise the window with content.
	 */
	protected void makeWindow() {
		clearContents();
	}
	
	/**
	 * Retrieve the contents of a file line by line.
	 * 
	 * @param path The path to the file.
	 * @return The lines of the processed file.
	 */
	public LinkedList<String> processFile(String path) throws FileNotFoundException, IOException {
		LinkedList<String> lines = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = reader.readLine();
			
		while(line != null) {
			lines.add(line);
			line = reader.readLine();
		}
		reader.close();
		
		return lines;
	}
	
	/**
	 * Display and wait for enter. Useful to pause states until user is ready.
	 */
	public void enterPrompt() {
		System.out.print("\nPlease press [ENTER] to continue... ");
		
		input.nextLine();
	}
	
}

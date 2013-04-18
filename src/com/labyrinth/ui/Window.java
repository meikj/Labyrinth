package com.labyrinth.ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Represents a basic text-based graphical window.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.17042013
 *
 */
public class Window {
	
	private String title;
	private String layoutPath;
	private LinkedList<String> window;
	protected LinkedList<String> contents;
	
	/**
	 * Construct a default window.
	 */
	public Window() {
		title = "";
		layoutPath = System.getProperty("user.dir") + "/media/window";
		window = new LinkedList<String>();
		contents = new LinkedList<String>();
		
		try {
			refresh();
		} catch(Exception e) {
			System.out.println("Fatal Error: Couldn't load default window layout.");
		}
	}
	
	/**
	 * Construct a window with a custom title.
	 * 
	 * @param title The title of the window.
	 */
	public Window(String title) {
		this();
		this.title = title;
		clearContents();
		
		try {
			refresh();
		} catch(Exception e) {
			System.out.println("Fatal Error: Couldn't load default window layout.");
		}
	}
	
	/**
	 * Displays the contents of the window.
	 */
	public void display() {
		for(String line : window) {
			if(line.contains("%")) {
				// Content starts wherever the % symbol is
				for(String content : contents) {
					// Add each entry in content to the window by adding it into the line
					String[] split = line.split("%");
					
					int offset = content.length() - 1;
					String newLine = split[0] + content + split[1].substring(offset, split[1].length());
					System.out.println(newLine);
				}
			} else {
				System.out.println(line);
			}
		}
	}
	
	/**
	 * Set the layout path to a new (custom) layout file. Custom elements include:
	 * 
	 * 	[W] = Black block
	 * 
	 * @param path The path to the new layout.
	 */
	public void setLayoutPath(String path) {
		layoutPath = path;
	}
	
	/**
	 * Refresh the window contents by internally redrawing the window.
	 * This should be called after setting a new layout path.
	 */
	public void refresh() throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(layoutPath));
		String line = reader.readLine();
		window = new LinkedList<String>();
		
		while(line != null) {
			// Do some replacements
			// [W] = wall block
			line = line.replace("[W]", Character.toString(CharacterElements.charBlock));
			window.add(line);
			
			line = reader.readLine();
		}
		reader.close();
	}
	
	/**
	 * Add a line of content to the window. Maximum length should be 80 characters to ensure 
	 * consistency.
	 * 
	 * @param content The line of content to add to the window.
	 * @throws IllegalArgumentException If the content passed is greater than 80 characters or is empty.
	 */
	public void addContent(String content) throws IllegalArgumentException {
		if(content.length() > 80 || content.length() < 1)
			throw new IllegalArgumentException("Content must not exceed 80 characters, nor be empty.");
		
		contents.add(content);
	}
	
	/**
	 * Clear the contents of the window.
	 */
	public void clearContents() {
		contents = new LinkedList<String>();
		
		// Add title to contents
		if(!title.isEmpty()) {
			contents.add(title);
			contents.add("------------------------------------------------------------------------");
			contents.add(" ");
		}
	}

}

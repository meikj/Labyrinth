package com.labyrinth.ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.labyrinth.SettingsManager;

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
	private LinkedList<String> contents;
	
	/**
	 * Construct a default window.
	 */
	public Window() {
		title = "";
		layoutPath = System.getProperty("user.dir") + "/media/window";
		window = new LinkedList<String>();
		contents = new LinkedList<String>();
	}
	
	/**
	 * Construct a window with a custom title.
	 * 
	 * @param title The title of the window.
	 */
	public Window(String title) {
		this();
		this.title = title;
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
					line = split[0] + content + split[1].substring(offset, split[1].length());
					System.out.println(line);
				}
			} else {
				System.out.println(line);
			}
		}
	}
	
	/**
	 * Set the layout path to a new (custom) layout file.
	 * 
	 * @param path The path to the new layout.
	 */
	public void setLayoutPath(String path) {
		layoutPath = path;
	}
	
	/**
	 * Refresh the window contents.
	 */
	public void refresh() throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(layoutPath));
		String line = reader.readLine();
		
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
	 * Add an item of content to the window. Maximum length should be 80 characters to ensure 
	 * consistency.
	 * 
	 * @param content The content to add.
	 * @throws IllegalArgumentException If the content passed is greater than 80 characters or empty.
	 */
	public void addContent(String content) throws IllegalArgumentException {
		if(content.length() > 80 || content.length() < 1)
			throw new IllegalArgumentException("Content must not exceed 80 characters, nor be empty.");
		
		contents.add(content);
	}
	
	/**
	 * Reset the window by emptying the contents and refreshing the layout.
	 */
	public void reset() {
		window = new LinkedList<String>();
		contents = new LinkedList<String>();
		
		try {
			refresh();
		} catch(Exception e) {
			System.out.println("Invalid layout path. Can't refresh() window.");
		}
	}

}

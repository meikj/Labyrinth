package com.labyrinth.ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Represents a basic text-based graphical window with no
 * user interaction.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.19042013
 *
 */
public class Window {
	
	private String title;
	private String layoutFile;
	private LinkedList<String> window;
	protected LinkedList<String> contents;
	
	/**
	 * Construct a default window.
	 */
	public Window() {
		title = "";
		layoutFile = "media/window_default";
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
	 * @param path The path to the new layout file.
	 */
	public void setLayoutFile(String path) {
		layoutFile = path;
	}
	
	/**
	 * Get the current layout file that is in use.
	 */
	public String getLayoutFile() {
		return layoutFile;
	}
	
	/**
	 * Refresh the window contents by internally redrawing the window.
	 * This should be called after setting a new layout path.
	 */
	public void refresh() throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(layoutFile));
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
	 * Add a line of content to the window. Maximum length should be 72 characters to ensure 
	 * consistency.
	 * 
	 * @param content The line of content to add to the window.
	 * @throws IllegalArgumentException If the content passed is greater than 72 characters.
	 */
	public void addContent(String content) throws IllegalArgumentException {
		if(content.length() > 72)
			throw new IllegalArgumentException("Content must not exceed 72 characters.");
		
		if(content.isEmpty())
			contents.add(" ");
		else
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

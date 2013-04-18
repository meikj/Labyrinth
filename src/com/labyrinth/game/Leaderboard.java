package com.labyrinth.game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents the leaderboard in the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.26032013
 *
 */
public class Leaderboard {
	
	private ArrayList<String> names;
	private ArrayList<Integer> scores;
	private String path;
	
	/**
	 * Construct a leader board from an existing leader board file.
	 * 
	 * @param path The leaderboard file to load.
	 */
	public Leaderboard(String path) {
		this.names = new ArrayList<String>();
		this.scores = new ArrayList<Integer>();
		this.path = path;
		
		try {
			load(path);
		} catch(FileNotFoundException e) {
			System.out.println("Error: Leaderboard file not found: " + path);
		} catch(IOException e) {
			System.out.println("Error: Leaderboard IO error: " + path);
		}
	}
	
	/**
	 * Submit a score to the leaderboard.
	 * 
	 * @param name The name of the player.
	 * @param score The score to submit.
	 */
	public void submit(String name, int score) {
		// Check if leaderboard is empty first
		if(scores.isEmpty()) {
			names.add(name);
			scores.add(score);
			return;
		}
		
		int placementIndex = 0;
		
		for(int i = 0; i < scores.size(); i++) {
			if(scores.get(i) > score) {
				placementIndex = i;
				break;
			}
		}
		
		names.add(placementIndex, name);
		scores.add(placementIndex, score);
	}
	
	
	/**
	 * Get the name entries in ascending order. Same index is used for scores.
	 */
	public ArrayList<String> getNames() {
		return names;
	}
	
	
	/**
	 * Get the score entries in ascending order. Same index is used for names.
	 */
	public ArrayList<Integer> getScores() {
		return scores;
	}
	
	/**
	 * Load a leaderboard from an existing file in the format of:
	 * 
	 * NAME SCORE
	 * NAME SCORE
	 * ...
	 * 
	 * Where the entries are in ascending order (i.e. entry 1 is number 1, entry 2 is number 2, etc.)
	 * 
	 * @param path The path to the leaderboard file.
	 * @throws IOException If a general IO error occurs.
	 * @throws FileNotFoundException If the leaderboard file is not found.
	 */
	public void load(String path) throws IOException, FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		Scanner scanner = null;
		
		String line = reader.readLine();
		
		while(line != null) {
			scanner = new Scanner(line);
			String[] entry;
			
			try {
				entry = scanner.nextLine().split(" ");
			} catch(NoSuchElementException e) {
				// No line was found, just skip
				line = reader.readLine();
				continue;
			}
			
			if(entry.length < 2) {
				scanner.close();
				reader.close();
				throw new IOException("Invalid entry in leaderboard!");
			}
			
			names.add(entry[0]);
			
			try {
				scores.add(Integer.parseInt(entry[1]));
			} catch(NumberFormatException e) {
				scanner.close();
				reader.close();
				throw new IOException("Invalid entry in leaderboard!");
			}
			
			scanner.close();
			line = reader.readLine();
		}
		
		reader.close();
	}
	
	/**
	 * Save the entries to the leaderboard file. Result is in ascending order:
	 * 
	 * NAME SCORE
	 * NAME SCORE
	 * ...
	 * 
	 * @throws IOException If an error occurs when writing to leaderboard file.
	 */
	public void save() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		// Save the leaderboard
		for(int i = 0; i < names.size(); i++) {
			writer.write(names.get(i) + " " + scores.get(i));
			writer.newLine();
		}

		writer.close();
	}

}

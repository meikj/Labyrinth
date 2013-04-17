package com.labyrinth;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.nio.charset.Charset;

/**
 * Manages settings and configurations for the Labyrinth game. Allows for the loading
 * and saving of configuration files. If an invalid or non-existant file is passed, the
 * default settings are used.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.24032013
 *
 */
public class SettingsManager {
	
	private int rows;
	private int columns;
	private String leaderboard;
	
	/**
	 * Construct a SettingsManager with default settings.
	 */
	public SettingsManager() {
		// Set to default
		initCharacters();
		setDefault();
	}
	
	/**
	 * Construct a SettingsManager with predefined settings. If the file
	 * is invalid or does not exist, then default settings are used.
	 * The user is notified of this via the standard output.
	 * 
	 * @param path The path to the settings file.
	 */
	public SettingsManager(String path) {
		this();
		
		// Set to 'erroneous' values first
		rows = 0;
		columns = 0;
		leaderboard = null;

		// Load settings from file
		try {
			load(path);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Note: Using default settings instead");
			setDefault();
		}
	}
	
	/**
	 * Set all the values in the settings to their default values.
	 */
	public void setDefault() {
		rows = 7;
		columns = 7;
		leaderboard = System.getProperty("user.dir") + "\\leaderboards\\leaderboard.txt";
	}
	
	/**
	 * Explicitly load settings in from a specified file path. Must be in the format:
	 * 
	 * ROWS <no_rows>
	 * COLUMNS <no_columns>
	 * LEADERBOARD <leaderboard_path>
	 * 
	 * @param path The path to the settings file.
	 * @throws IOException Thrown when the file is not found or invalid.
	 */
	public void load(String path) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Scanner scanner = null;

			String line = reader.readLine();

			while(line != null) {
				scanner = new Scanner(line);
				String[] entry = null;
				
				try {
					entry = scanner.nextLine().split(" ");
				} catch(NoSuchElementException e) {
					// Empty line, just skip
					line = reader.readLine();
					continue;
				}
				
				if(entry.length < 2) {
					scanner.close();
					reader.close();
					throw new IOException("Invalid entry in settings file!");
				}

				if(entry[0].equals("ROWS")) {
					// Line contains ROWS value
					int r;

					try {
						r = Integer.parseInt(entry[1]);
						
						if((r % 2 == 0) || (r < 5) || (r > 15)) {
							// Violates row rules
							scanner.close();
							reader.close();
							throw new IOException("Rows must be odd, and between 5 and 15 (inclusive)");
						}
					}
					catch(NumberFormatException e) {
						scanner.close();
						reader.close();
						throw new IOException("Settings file is invalid: '" + entry[0] + "' contains invalid value.");
					}

					// Set rows to that specified in settings file
					this.rows = r;
				}
				else if(entry[0].equals("COLUMNS")) {
					// Line contains ROWS value
					int c;

					try {
						c = Integer.parseInt(entry[1]);
						
						if((c % 2 == 0) || (c < 5) || (c > 15)) {
							// Violates row rules
							scanner.close();
							reader.close();
							throw new IOException("Columns must be odd, and between 5 and 15 (inclusive)");
						}
					}
					catch(NumberFormatException e) {
						scanner.close();
						reader.close();
						throw new IOException("Settings file is invalid: '" + entry[0] + "' contains invalid value.");
					}

					// Set columns to that specified in settings file
					this.columns = c;
				}
				else if(entry[0].equals("LEADERBOARD")) {
					// Set leaderboard to that specified in settings file
					this.leaderboard = System.getProperty("user.dir") + entry[1];
				}
				else {
					// Invalid type?
					scanner.close();
					reader.close();
					throw new IOException("Settings file is invalid: '" + entry[0] + "' is an invalid type.");
				}

				line = reader.readLine();
			}

			scanner.close();
			reader.close();
		}
		catch(FileNotFoundException e) {
			throw new IOException("Settings file not found: path = " + path);
		}

		// Check if any values remain unset, if so set to default value(s)
		if(rows == 0) {
			rows = 7;
		}
		if(columns == 0) {
			columns = 7;
		}
		if(leaderboard == null) {
			leaderboard = System.getProperty("user.dir") + "\\leaderboards\\leaderboard.txt";
		}
	}

	/**
	 * Save the current defined settings to a specified settings file. Will be in
	 * the format of:
	 * 
	 * ROWS <no_rows>
	 * COLUMNS <no_columns>
	 * LEADERBOARD <leaderboard_path>
	 * 
	 * @param path The path to the settings file.
	 * @throws IOException Thrown when can't write to file.
	 */
	public void save(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		// Save the settings
		writer.write("ROWS " + rows);
		writer.newLine();
		writer.write("COLUMNS " + columns);
		writer.newLine();
		writer.write("LEADERBOARD " + leaderboard);
		writer.newLine();

		writer.close();
	}
	
	/**
	 * Get the number of rows.
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Get the number of columns.
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * Get the leaderboard path.
	 */
	public String getLeaderboard() {
		return leaderboard;
	}
	
	/**
	 * Set the number of rows.
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**
	 * Set the number of columns.
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	/**
	 * Set the leaderboard path.
	 */
	public void setLeaderboard(String leaderboard) {
		this.leaderboard = leaderboard;
	}
	
}

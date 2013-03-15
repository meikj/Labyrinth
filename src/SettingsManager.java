import java.io.*;
import java.util.Scanner;

/**
 * Manages settings and configurations for the Labyrinth game. Allows for the loading
 * and saving of configuration files.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.15032013
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
		setDefault();
	}
	
	/**
	 * Construct a SettingsManager with predefined settings.
	 * 
	 * @param path The path to the settings file.
	 */
	public SettingsManager(String path) {
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
		leaderboard = System.getProperty("user.dir") + "\\leaderboard.txt";
	}
	
	/**
	 * Load settings in from a specified file path. Must be in the format:
	 * 
	 * ROWS <no_rows>
	 * COLUMNS <no_columns>
	 * LEADERBOARD <leaderboard_path>
	 * 
	 * @param path The path to the settings file.
	 * @throws IOException When the file is not found or invalid.
	 */
	private void load(String path) throws IOException {
		throw new IOException();
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public String getLeaderboard() {
		return leaderboard;
	}
	
}

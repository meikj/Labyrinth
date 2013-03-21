import java.io.*;
import java.util.Scanner;

/**
 * Manages settings and configurations for the Labyrinth game. Allows for the loading
 * and saving of configuration files.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.20032013
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
	 * Load settings in from a specified file path. Must be in the format:
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
				String type = scanner.next(); // i.e. ROWS, COLUMNS, etc.

				if(type.equals("ROWS")) {
					// Line contains ROWS value
					int r;

					try {
						r = scanner.nextInt();
					}
					catch(NumberFormatException e) {
						scanner.close();
						reader.close();
						throw new IOException("Settings file is invalid: '" + type + "' contains invalid value.");
					}

					// Set rows to that specified in settings file
					this.rows = r;
				}
				else if(type.equals("COLUMNS")) {
					// Line contains ROWS value
					int c;

					try {
						c = scanner.nextInt();
					}
					catch(NumberFormatException e) {
						scanner.close();
						reader.close();
						throw new IOException("Settings file is invalid: '" + type + "' contains invalid value.");
					}

					// Set columns to that specified in settings file
					this.columns = c;
				}
				else if(type.equals("LEADERBOARD")) {
					// Line contains ROWS value
					String l;

					try {
						l = scanner.nextLine();
					}
					catch(NumberFormatException e) {
						scanner.close();
						reader.close();
						throw new IOException("Settings file is invalid: '" + type + "' contains invalid value.");
					}

					// Set leaderboard to that specified in settings file
					this.leaderboard = l;
				}
				else {
					// Invalid type?
					scanner.close();
					reader.close();
					throw new IOException("Settings file is invalid: '" + type + "' is an invalid type.");
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

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public String getLeaderboard() {
		return leaderboard;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setLeaderboard(String leaderboard) {
		this.leaderboard = leaderboard;
	}
	
}

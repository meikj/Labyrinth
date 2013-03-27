import java.io.*;

/**
 * Allows for the loading and saving of games.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.270312
 *
 */
public class GameManager {
	
	/**
	 * Load in and parse a save game. This method relies on the file to be in the
	 * following format:
	 * 
	 * ROWS <no>
	 * COLUMNS <no>
	 * GAME_MODE <mode>
	 * TILE <type> <rotation>
	 * ... (ROWS x COLUMNS)
	 * TILE <type> <rotation>
	 * TOKEN <column> <row>
	 * TREAURE <column> <row>
	 * 
	 * @param path The path to the save game.
	 * @return The result in the form of a GameMode.
	 */
	public GameMode load(String path) throws FileNotFoundException, IOException {
		GameMode game = null;
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		SettingsManager settings = new SettingsManager();
		int nextColumn = 1, nextRow = 1;
		String line = reader.readLine();
		
		while(line != null) {
			String[] lineTokens = line.split(" ");
			
			if(lineTokens[0].equals("ROWS")) {
				// Process rows
				settings.setRows(Integer.parseInt(lineTokens[1]));
			} else if(lineTokens[0].equals("COLUMNS")) {
				// Process columns
				settings.setColumns(Integer.parseInt(lineTokens[1]));
			} else if(lineTokens[0].equals("GAME_MODE")) {
				// Process game mode
				if(lineTokens[1].equals("tc")) {
					game = new TreasureChase(settings);
				}
			} else if(lineTokens[0].equals("TILE")) {
				// Process tile
				if(nextColumn > settings.getColumns()) {
					// Reset column, increase row
					nextColumn = 1;
					nextRow++;
				}
				
				// Tile properties
				TileType tileType;
				RotationAngle rotationAngle;
				Tile newTile;
				
				// Tile type is 2nd element
				tileType = TileType.valueOf(lineTokens[1]);
				
				// Rotation angle is 3rd element
				rotationAngle = RotationAngle.valueOf(lineTokens[2]);
				
				// Form new tile
				newTile = new Tile(tileType, rotationAngle);
				
				// Check if immovable
				if((nextColumn % 2 != 0) && (nextRow % 2 != 0))
					newTile.setMovable(false);
				
				game.getBoard().setTile(nextColumn, nextRow, newTile);
				
				nextColumn++;
			} else if(lineTokens[0].equals("TOKEN")) {
				game.getBoard().getTile(Integer.parseInt(lineTokens[1]), Integer.parseInt(lineTokens[2])).setToken(true);
			} else if(lineTokens[0].equals("TREASURE")) {
				game.getBoard().getTile(Integer.parseInt(lineTokens[1]), Integer.parseInt(lineTokens[2])).setTreasure(true);
			}
			
			line = reader.readLine();
		}
		
		reader.close();
		return game;
	}
	
	/**
	 * Save a game to the specified path.
	 */
	public void save(String path, GameMode game) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		
		// Get the game properties
		int rows = game.getSettings().getRows();
		int columns = game.getSettings().getColumns();
		String gameMode = game.getClass().getName().equals("TreasureChase") ? "tc" : "lc";
		
		writer.write("ROWS " + rows);
		writer.newLine();
		writer.write("COLUMNS " + columns);
		writer.newLine();
		writer.write("GAME_MODE " + gameMode);
		writer.newLine();
		
		for(int i = 1; i <= rows; i++) {
			for(int j = 1; j <= columns; j++) {
				Tile t = game.getBoard().getTile(j, i);
				
				writer.write("TILE " + t.getType() + " " + t.getRotation());
				writer.newLine();
			}
		}
		
		writer.write("TOKEN " + game.getBoard().getTokenPos()[0] + " " + game.getBoard().getTokenPos()[1]);
		writer.newLine();
		
		writer.write("TREASURE " + game.getBoard().getTreasurePos()[0] + " " + game.getBoard().getTreasurePos()[1]);
		writer.newLine();
		
		writer.close();
	}
	
	/**
	 * Add an entry of a saved game to the entry list.
	 * 
	 * @param name The name of the saved game.
	 */
	public void addGameEntry(String name) throws IOException {
		String path = System.getProperty("user.dir") + "/saves/list.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		
		writer.write(name);
		writer.newLine();
		writer.close();
	}

}

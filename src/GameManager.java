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
					System.out.println("Initialising TC");
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
				
				System.out.println("Setting tile (" + nextColumn + "," + nextRow + ")");
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

}

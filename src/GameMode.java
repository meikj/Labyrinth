/**
 * Represents a game mode in the Labyrinth game. The two game modes planned
 * are Treasure Chase and Word Chase. At the moment only Treasure Chase is
 * being implemented
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.07032013
 *
 */
public interface GameMode {
	
	/**
	 * Move the token to a particular tile
	 * 
	 * @param row The row coordinate of the tile
	 * @param column The column coordinate of the tile
	 */
	public void moveToken(int row, int column);
	
	/**
	 * Move an existing tile on the board to a new location
	 * 
	 * @param currRow The current row coordinate of the tile
	 * @param currColumn The current column coordinate of the tile
	 * @param newRow The new row coordinate of the tile
	 * @param newColumn The new column coordinate of the tile
	 */
	public void moveTile(int currRow, int currColumn, int newRow, int newColumn);
	
	/**
	 * Rotate the spare tile to a particular rotation
	 * 
	 * @param angle The angle to rotate the spare tile by
	 */
	public void rotateTile(int angle);
	
	/**
	 * Rotate the tile specified to a particular rotation
	 * 
	 * @param row The row coordinate of the existing tile
	 * @param column The column coordinate of the existing tile
	 * @param angle The angle to rotate the tile by
	 */
	public void rotateTile(int row, int column, int angle);
	
	/**
	 * Replace an existing tile on the board with the Player's spare tile
	 * 
	 * @param row The row coordinate of the existing tile
	 * @param column The column coordinate of the existing tile
	 */
	public void replaceTile(int row, int column);
	
	/**
	 * Replace an existing tile on the board with a new tile
	 * 
	 * @param row The row coordinate of the existing tile
	 * @param column The column coordinate of the existing tile
	 * @param newTile The new tile to place over an existing tile
	 */
	public void replaceTile(int row, int column, Tile newTile);
	
	/**
	 * Update the interface
	 */
	public void update();
	
	/**
	 * Save the game
	 */
	public void save();
	
	/**
	 * End the game
	 */
	public void end();
	
}

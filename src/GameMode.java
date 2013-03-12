/**
 * Represents a game mode in the Labyrinth game. The two game modes planned
 * are Treasure Chase and Word Chase. At the moment only Treasure Chase is
 * being implemented.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.12032013
 *
 */
public interface GameMode {
	
	/**
	 * Move the token up a tile.
	 */
	public void moveTokenUp();
	
	/**
	 * Move the token down a tile.
	 */
	public void moveTokenDown();
	
	/**
	 * Move the token left a tile.
	 */
	public void moveTokenLeft();
	
	/**
	 * Move the token right a tile.
	 */
	public void moveTokenRight();
	
	/**
	 * Rotate the spare tile by a particular angle.
	 * 
	 * @param angle The angle to rotate the spare tile by.
	 */
	public void rotateTile(int angle);
	
	/**
	 * Rotate the tile specified by a particular angle.
	 * 
	 * @param row The row coordinate of the existing tile.
	 * @param column The column coordinate of the existing tile.
	 * @param angle The angle to rotate the tile by.
	 */
	public void rotateTile(int row, int column, int angle);
	
	/**
	 * Insert the spare tile into the specified row.
	 * 
	 * @param row The row to insert the spare tile into.
	 */
	public void insertRow(int row);
	
	/**
	 * Insert the spare tile into the specified column.
	 * 
	 * @param column The column to insert the spare tile into.
	 */
	public void insertColumn(int column);
	
	/**
	 * Update the interface.
	 */
	public void update();
	
	/**
	 * Save the game.
	 */
	public void save();
	
	/**
	 * End the game.
	 */
	public void end();
	
	/**
	 * Check if the user has won the game.
	 */
	public boolean hasWon();
	
	/**
	 * Called when the player has won the game.
	 */
	public void onWin();
	
}

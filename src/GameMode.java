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
	 * @throws IllegalMoveException When a wall is encountered.
	 */
	public void moveTokenUp() throws IllegalMoveException;
	
	/**
	 * Move the token down a tile.
	 * @throws IllegalMoveException When a wall is encountered.
	 */
	public void moveTokenDown() throws IllegalMoveException;
	
	/**
	 * Move the token left a tile.
	 * @throws IllegalMoveException When a wall is encountered.
	 */
	public void moveTokenLeft() throws IllegalMoveException;
	
	/**
	 * Move the token right a tile.
	 * @throws IllegalMoveException When a wall is encountered.
	 */
	public void moveTokenRight() throws IllegalMoveException;
	
	/**
	 * Rotate the spare tile by a particular angle.
	 * 
	 * @param angle The angle to rotate the spare tile by.
	 * @throws NumberFormatException When angle is not 90, 180 or 270.
	 */
	public void rotateTile(int angle) throws NumberFormatException;
	
	/**
	 * Insert the spare tile into the specified row.
	 * 
	 * @param row The row to insert the spare tile into.
	 * @throws IllegalMoveException When the move cannot be accomplished due to immovable tiles, etc.
	 */
	public void insertRow(int row, Direction direc) throws IllegalMoveException;
	
	/**
	 * Insert the spare tile into the specified column.
	 * 
	 * @param column The column to insert the spare tile into.
	 * @throws IllegalMoveException When the move cannot be accomplished due to immovable tiles, etc.
	 */
	public void insertColumn(int column, Direction direc) throws IllegalMoveException;
	
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

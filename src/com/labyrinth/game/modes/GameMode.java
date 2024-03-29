package com.labyrinth.game.modes;

import com.labyrinth.game.Board;
import com.labyrinth.game.ComputerPlayer;
import com.labyrinth.game.Direction;
import com.labyrinth.game.IllegalMoveException;
import com.labyrinth.game.Leaderboard;
import com.labyrinth.game.Player;
import com.labyrinth.game.SettingsManager;

/**
 * Represents a general game mode.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.01042013
 *
 */
public interface GameMode {
	
	/**
	 * Move the token up a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenUp() throws IllegalMoveException;
	
	/**
	 * Move the token down a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenDown() throws IllegalMoveException;
	
	/**
	 * Move the token left a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenLeft() throws IllegalMoveException;
	
	/**
	 * Move the token right a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
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
	 * @param direc The direction from which to insert the tile into.
	 * @param performer The player who is performing the move.
	 * @return The newly updated player with increased score/moves and spare tile based on performer.
	 * @throws IllegalMoveException When the move cannot be accomplished due to immovable tiles, etc.
	 */
	public Player insertRow(int row, Direction direc, Player performer) throws IllegalMoveException;
	
	/**
	 * Insert the spare tile into the specified column.
	 * 
	 * @param column The column to insert the spare tile into.
	 * @param direc The direction from which to insert the tile into.
	 * @param performer The player who is performing the move.
	 * @return The newly updated player with increased score/moves and spare tile based on performer.
	 * @throws IllegalMoveException When the move cannot be accomplished due to immovable tiles, etc.
	 */
	public Player insertColumn(int column, Direction direc, Player performer) throws IllegalMoveException;
	
	/**
	 * Update the player with a new player.
	 * 
	 * @param p The new player.
	 */
	public void updatePlayer(Player p);
	
	/**
	 * Transition to the next round.
	 */
	public void nextRound();
	
	/**
	 * Check if the user has won the game.
	 */
	public boolean hasWon();
	
	/**
	 * Get the active game board.
	 */
	public Board getBoard();
	
	/**
	 * Get the active player.
	 */
	public Player getPlayer();
	
	/**
	 * Get the active computer player opponent.
	 */
	public ComputerPlayer getComputerPlayer();
	
	/**
	 * Get the current round.
	 */
	public int getRound();
	
	/**
	 * Get the active leaderboard.
	 */
	public Leaderboard getLeaderboard();
	
	/**
	 * Get the active settings.
	 */
	public SettingsManager getSettings();
	
	/**
	 * Set the current round number.
	 */
	public void setRound(int round);
	
	/**
	 * Set the current move of the game.
	 */
	public void setCurrentMove(String move);
	
	/**
	 * Get the current move of the game.
	 */
	public String getCurrentMove();
	
}

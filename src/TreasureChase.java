import java.util.Random;

/**
 * Represents the Treasure Chase game mode.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.24032013
 *
 */
public class TreasureChase implements GameMode {
	
	private Player player;
	private Board board;
	private Leaderboard leaderboard;
	private int round;
	private SettingsManager settings;
	private ComputerPlayer computer;
	private Random rand;
	
	/**
	 * Construct a TreasureChase game with a specified player.
	 * 
	 * @param settings The settings to use for the game.
	 */
	public TreasureChase(SettingsManager settings) {
		this.player = new Player();
		this.leaderboard = new Leaderboard(settings.getLeaderboard());
		this.settings = settings;
		
		// Generate a board with settings specified in the SettingsManager
		this.board = new Board(settings.getColumns(), settings.getRows());
		
		// Set a random tile on the board to contain treasure (disclude 1,1)
		this.rand = new Random();
		int rCol = rand.nextInt(settings.getColumns()) + 1;
		int rRow = rand.nextInt(settings.getRows()) + 1;
		
		while(rCol == 1 && rRow == 1) {
			rCol = rand.nextInt(settings.getColumns()) + 1;
			rRow = rand.nextInt(settings.getRows()) + 1; 
		}
		
		this.board.getTile(rCol, rRow).setTreasure(true);
		this.board.setTreasurePos(rCol, rRow);
		
		// Initialise the computer opponent
		this.computer = new ComputerPlayer(this.board);
	}
	
	/**
	 * Move the token up a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenUp() throws IllegalMoveException {
		int newRow = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosRow == board.getHeight()) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getNorth() ||
					!board.getTile(tokenPosCol, 1).getSouth()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
			// Transition token over board Pacman style
			newRow = 1;
		}
		else {
			// Won't go over board, but we need to check if a wall is blocking movement
			newRow = tokenPosRow + 1;
			
			// Check if current tile north entry is available, and new tile south
			if(!board.getTile(tokenPosCol, tokenPosRow).getNorth() ||
					!board.getTile(tokenPosCol, newRow).getSouth()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(tokenPosCol, newRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
	}
	
	/**
	 * Move the token down a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenDown() throws IllegalMoveException {
		int newRow = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosRow == 1) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getSouth() ||
					!board.getTile(tokenPosCol, board.getHeight()).getNorth()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
			// Transition token over board Pacman style
			newRow = board.getHeight();
		}
		else {
			// Won't go over board, but we need to check if a wall is blocking movement
			newRow = tokenPosRow - 1;
			
			// Check if current tile north entry is available, and new tile south
			if(!board.getTile(tokenPosCol, tokenPosRow).getSouth() ||
					!board.getTile(tokenPosCol, newRow).getNorth()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(tokenPosCol, newRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
	}
	
	/**
	 * Move the token left a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenLeft() throws IllegalMoveException { 
		int newCol = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosCol == 1) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getWest() ||
					!board.getTile(board.getWidth(), tokenPosRow).getEast()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
			// Transition token over board Pacman style
			newCol = board.getWidth();
		}
		else {
			// Won't go over board, but we need to check if a wall is blocking movement
			newCol = tokenPosCol - 1;
			
			// Check if current tile north entry is available, and new tile south
			if(!board.getTile(tokenPosCol, tokenPosRow).getWest() ||
					!board.getTile(newCol, tokenPosRow).getEast()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(newCol, tokenPosRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
	}
	
	/**
	 * Move the token right a tile.
	 * 
	 * @throws IllegalMoveException Thrown when a wall is encountered.
	 */
	public void moveTokenRight() throws IllegalMoveException { 
		int newCol = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosCol == board.getWidth()) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getEast() ||
					!board.getTile(board.getWidth(), tokenPosRow).getWest()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
			// Transition token over board Pacman style
			newCol = 1;
		}
		else {
			// Won't go over board, but we need to check if a wall is blocking movement
			newCol = tokenPosCol + 1;
			
			// Check if current tile north entry is available, and new tile south
			if(!board.getTile(tokenPosCol, tokenPosRow).getEast() ||
					!board.getTile(newCol, tokenPosRow).getWest()) {
				// Can't pass through, failed
				throw new IllegalMoveException("Can't pass through walls!");
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(newCol, tokenPosRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
	}
	
	/**
	 * Rotate the spare tile by a particular angle.
	 * 
	 * @param angle The angle to rotate the spare tile by. Must be either 90, 180 or 270.
	 */
	public void rotateTile(int angle) throws NumberFormatException {
		if(angle < 0 || angle > 270)
			// Angle is out of bounds
			throw new NumberFormatException("Angle must either be 90, 180 or 270");
		
		if((angle % 90) != 0)
			// Angle isn't divisible by 90
			throw new NumberFormatException("Angle must either be 90, 180 or 270");
		
		int currAngle = RotationAngle.convertToInt(player.getSpareTile().getRotation());
		RotationAngle newAngle = RotationAngle.convertFromInt((angle + currAngle) % 360);
		player.getSpareTile().setRotation(newAngle);
		
		// Update player move
		player.updateLastMove("rotate " + angle);
	}
	
	/**
	 * Insert the spare tile into the specified row.
	 * 
	 * @param row The row to insert the spare tile into.
	 * @param direc The direction from which to insert the tile into.
	 * @param performer The player who is performing the move.
	 * @return The newly updated player with increased score/moves and spare tile based on performer.
	 * @throws IllegalMoveException When the move cannot be accomplished due to immovable tiles, etc.
	 */
	public Player insertRow(int row, Direction direc, Player performer) throws IllegalMoveException {
		// Check first if move is inserting back into same place
		String[] lastPlayerMove = performer.getLastMove().split(" ");
		
		if(lastPlayerMove.length == 4) {
			// Valid insert move was last made, do some further checking
			if(lastPlayerMove[0].equals("insert") && 
					lastPlayerMove[1].equals("row") && 
					lastPlayerMove[3].equals(Integer.toString(row))) {
				if(direc == Direction.LEFT) {
					// Can't insert from right this time
					if(lastPlayerMove[2].equals("right")) {
						throw new IllegalMoveException("Can't insert tile back into same position");
					}
				} else if(direc == Direction.RIGHT) {
					// Can't insert from left this time
					if(lastPlayerMove[2].equals("left")) {
						throw new IllegalMoveException("Can't insert tile back into same position");
					}
				}
			}
		}
			
		Tile newSpareTile = null;
		
		if(direc == Direction.LEFT)
			newSpareTile = insertTileRowLeft(row, performer.getSpareTile());
		else if(direc == Direction.RIGHT)
			newSpareTile = insertTileRowRight(row, performer.getSpareTile());
		else
			throw new IllegalMoveException("Invalid direction specified");
		
		if(newSpareTile != null) {
			// Success! New spare tile returned
			performer.setSpareTile(newSpareTile);
			
			// Update player move
			performer.updateLastMove("insert row " + direc.toString().toLowerCase() + " " + row);
		} else {
			throw new IllegalMoveException("Could not retrieve new spare tile");
		}
		
		return performer;
	}
	
	/**
	 * Insert a specific tile into the specified row from the left hand side.
	 * 
	 * @param row The row to insert the tile to.
	 * @param newTile The tile to insert into the row.
	 * @return The tile that falls off the side of the board.
	 */
	private Tile insertTileRowLeft(int row, Tile newTile) throws IllegalMoveException {
		// Check if any tiles on row are immovable/fixed
		for(int i = 1; i < board.getWidth(); i++) {
			try {
				if(!board.getTile(i, row).isMovable()) {
					// Found an immovable tile
					throw new IllegalMoveException("Specified row contains one or more immovable tiles");
				}
			} catch(IllegalArgumentException e) {
				throw new IllegalMoveException(e.getMessage());
			}
		}
		
		// Set the spare tile to the tile that will fall off
		Tile spareTile = board.getTile(board.getWidth(), row);
		
		if(spareTile.hasToken()) {
			// The tile that is going to fall off contains the token, so set token to new tile
			spareTile.setToken(false);
			newTile.setToken(true);
			board.setTokenPos(1, row);
		}
		else if(spareTile.hasTreasure()) {
			// The tile that is going to fall off contains the treasure, so set treasure to new tile
			spareTile.setTreasure(false);
			newTile.setTreasure(true);
			board.setTreasurePos(1, row);
		}
		
		// Push all tiles across
		for(int i = board.getWidth(); i > 1; i--) {
			// Set tile to preceding tile
			Tile t = board.getTile(i - 1, row);
			
			// Check if preceding tile contains token
			if(t.hasToken()) {
				// If so, set token position to new position
				board.setTokenPos(i, row);
			}
			
			board.setTile(i, row, t);
		}
		
		// Set the first tile in row to new tile
		board.setTile(1, row, newTile);
		
		return spareTile;
	}
	
	/**
	 * Insert a specific tile into the specified row from the right hand side.
	 * 
	 * @param row The row to insert the tile to.
	 * @param newTile The tile to insert into the row.
	 * @return The tile that falls off the side of the board.
	 */
	private Tile insertTileRowRight(int row, Tile newTile) throws IllegalMoveException {
		// Check if any tiles on row are immovable/fixed
		for(int i = 1; i < board.getWidth(); i++) {
			try {
				if(!board.getTile(i, row).isMovable()) {
					// Found an immovable tile
					throw new IllegalMoveException("Specified row contains one or more immovable tiles");
				}
			} catch(IllegalArgumentException e) {
				throw new IllegalMoveException(e.getMessage());
			}
		}
		
		// Set the spare tile to the tile that will fall off
		Tile spareTile = board.getTile(1, row);
		
		if(spareTile.hasToken()) {
			// The tile that is going to fall off contains the token, so set token to new tile
			spareTile.setToken(false);
			newTile.setToken(true);
			board.setTokenPos(board.getWidth(), row);
		}
		else if(spareTile.hasTreasure()) {
			// The tile that is going to fall off contains the treasure, so set treasure to new tile
			spareTile.setTreasure(false);
			newTile.setTreasure(true);
			board.setTreasurePos(board.getWidth(), row);
		}
		
		// Push all tiles across
		for(int i = 1; i < board.getWidth(); i++) {
			// Set tile to proceeding tile
			Tile t = board.getTile(i + 1, row);
			
			// Check if proceeding tile contains token
			if(t.hasToken()) {
				// If so, set token position to new position
				board.setTokenPos(i, row);
			}
			
			board.setTile(i, row, t);
		}
		
		// Set the last tile in row to new tile
		board.setTile(board.getWidth(), row, newTile);
		
		return spareTile;
	}
	
	/**
	 * Insert the spare tile into the specified column.
	 * 
	 * @param column The column to insert the spare tile into.
	 * @param direc The direction from which to insert the tile into.
	 * @param performer The player who is performing the move.
	 * @return The newly updated player with increased score/moves and spare tile based on performer.
	 * @throws IllegalMoveException When the move cannot be accomplished due to immovable tiles, etc.
	 */
	public Player insertColumn(int column, Direction direc, Player performer) throws IllegalMoveException {
		// Check first if move is inserting back into same place
		String[] lastPlayerMove = performer.getLastMove().split(" ");
		
		if(lastPlayerMove.length == 4) {
			// Valid insert move was last made, do some further checking
			if(lastPlayerMove[0].equals("insert") && 
					lastPlayerMove[1].equals("column") && 
					lastPlayerMove[3].equals(Integer.toString(column))) {
				if(direc == Direction.TOP) {
					// Can't insert from bottom this time
					if(lastPlayerMove[2].equals("bottom")) {
						throw new IllegalMoveException("Can't insert tile back into same position");
					}
				} else if(direc == Direction.BOTTOM) {
					// Can't insert from top this time
					if(lastPlayerMove[2].equals("top")) {
						throw new IllegalMoveException("Can't insert tile back into same position");
					}
				}
			}
		}
		
		Tile newSpareTile = null;
		
		if(direc == Direction.BOTTOM)
			newSpareTile = insertTileColumnBottom(column, performer.getSpareTile());
		else if(direc == Direction.TOP)
			newSpareTile = insertTileColumnTop(column, performer.getSpareTile());
		else
			throw new IllegalMoveException("Invalid direction specified");
		
		if(newSpareTile != null) {
			// Success! New spare tile returned
			performer.setSpareTile(newSpareTile);
			
			// Update player move
			performer.updateLastMove("insert column " + direc.toString().toLowerCase() + " " + column);
		} else {
			throw new IllegalMoveException("Could not retrieve new spare tile");
		}
		
		return performer;
	}
	
	/**
	 * Insert a specific tile into the specified column from the bottom.
	 * 
	 * @param column The column to insert the tile to.
	 * @param newTile The tile to insert into the column.
	 * @return The tile that falls off the side of the board.
	 */
	private Tile insertTileColumnBottom(int column, Tile newTile) throws IllegalMoveException {
		// Check if any tiles in column are immovable/fixed
		for(int i = 1; i < board.getHeight(); i++) {
			try {
				if(!board.getTile(column, i).isMovable()) {
					// Found an immovable tile
					throw new IllegalMoveException("Specified column contains one or more immovable tiles");
				}
			} catch(IllegalArgumentException e) {
				throw new IllegalMoveException(e.getMessage());
			}
		}
		
		// Set the spare tile to the tile that will fall off
		Tile spareTile = board.getTile(column, board.getHeight());
		
		if(spareTile.hasToken()) {
			// The tile that is going to fall off contains the token, so set token to new tile
			spareTile.setToken(false);
			newTile.setToken(true);
			board.setTokenPos(column, 1);
		}
		else if(spareTile.hasTreasure()) {
			// The tile that is going to fall off contains the treasure, so set treasure to new tile
			spareTile.setTreasure(false);
			newTile.setTreasure(true);
			board.setTreasurePos(column, 1);
		}
		
		// Push all tiles across
		for(int i = board.getHeight(); i > 1; i--) {
			// Set tile to preceding tile
			Tile t = board.getTile(column, i - 1);
			
			// Check if preceding tile contains token
			if(t.hasToken()) {
				// If so, set token position to new position
				board.setTokenPos(column, i);
			}
			
			board.setTile(column, i, t);
		}
		
		// Set the first tile in row to new tile
		board.setTile(column, 1, newTile);
		
		return spareTile;
	}
	
	/**
	 * Insert a specific tile into the specified column from the top.
	 * 
	 * @param column The column to insert the tile to.
	 * @param newTile The tile to insert into the column.
	 * @return The tile that falls off the side of the board.
	 */
	private Tile insertTileColumnTop(int column, Tile newTile) throws IllegalMoveException {
		// Check if any tiles in column are immovable/fixed
		for(int i = 1; i < board.getHeight(); i++) {
			try {
				if(!board.getTile(column, i).isMovable()) {
					// Found an immovable tile
					throw new IllegalMoveException("Specified column contains one or more immovable tiles");
				}
			} catch(IllegalArgumentException e) {
				throw new IllegalMoveException(e.getMessage());
			}
		}
		
		// Set the spare tile to the tile that will fall off
		Tile spareTile = board.getTile(column, 1);
		
		if(spareTile.hasToken()) {
			// The tile that is going to fall off contains the token, so set token to new tile
			spareTile.setToken(false);
			newTile.setToken(true);
			board.setTokenPos(column, board.getHeight());
		}
		else if(spareTile.hasTreasure()) {
			// The tile that is going to fall off contains the treasure, so set treasure to new tile
			spareTile.setTreasure(false);
			newTile.setTreasure(true);
			board.setTreasurePos(column, board.getHeight());
		}
		
		// Push all tiles across
		for(int i = 1; i < board.getHeight(); i++) {
			// Set tile to proceeding tile
			Tile t = board.getTile(column, i + 1);
			
			// Check if proceeding tile contains token
			if(t.hasToken()) {
				// If so, set token position to new position
				board.setTokenPos(column, i);
			}
			
			board.setTile(column, i, t);
		}
		
		// Set the first tile in row to new tile
		board.setTile(column, board.getHeight(), newTile);
		
		return spareTile;
	}
	
	/**
	 * Update the player with a new player. Useful when performing a tile move.
	 * 
	 * @param p The new player.
	 */
	public void updatePlayer(Player p) {
		player = p;
	}
	
	/**
	 * Transition to the next round.
	 */
	public void nextRound() {
		// Increment round and score
		round++;
		player.setScore(player.getScore() + 1);
		
		if(!hasWon()) {
			// Make computer move if win condition not satisfied
			computerMove();
		}
	}
	
	/**
	 * Save the current progress of the game.
	 * 
	 * @param name The name of the saved game.
	 */
	public void save(String name) {
		//
		// TODO
		//
		return;
	}
	
	/**
	 * Check if the player has won by analysing the token position (i.e. if it contains treasure).
	 * 
	 * @return Whether or not the player has satisfied the win conditions.
	 */
	public boolean hasWon() {
		int[] tokenPos = board.getTokenPos();
		
		if(board.getTile(tokenPos[0], tokenPos[1]).hasTreasure())
			return true;
		else
			return false;
	}
	
	/**
	 * Perform a random computer tile move.
	 */
	private void computerMove() {
		// Randomly choose between row or column
		int r1 = rand.nextInt(2); // row/column
		
		if(r1 == 0) {
			// Row
			
			// Randomly choose between left or right insertion
			int r2 = rand.nextInt(2);
			
			if(r2 == 0) {
				// Left
				try {
					computer = (ComputerPlayer) insertRow(computer.getRandomRow(), Direction.LEFT, computer);
				} catch (IllegalMoveException e) {
					// In the rare case the computer makes an illegal move, redo the move
					computerMove();
					return;
				}
			} else {
				// Right
				try {
					computer = (ComputerPlayer) insertRow(computer.getRandomRow(), Direction.RIGHT, computer);
				} catch (IllegalMoveException e) {
					// In the rare case the computer makes an illegal move, redo the move
					computerMove();
					return;
				}
			}
		} else {
			// Column
			
			// Randomly choose between top or bottom insertion
			int r2 = rand.nextInt(2);
			
			if(r2 == 0) {
				// Top
				try {
					computer = (ComputerPlayer) insertColumn(computer.getRandomColumn(), Direction.TOP, computer);
				} catch (IllegalMoveException e) {
					// In the rare case the computer makes an illegal move, redo the move
					computerMove();
					return;
				}
			} else {
				// Bottom
				try {
					computer = (ComputerPlayer) insertColumn(computer.getRandomColumn(), Direction.BOTTOM, computer);
				} catch (IllegalMoveException e) {
					// In the rare case the computer makes an illegal move, redo the move
					computerMove();
					return;
				}
			}
		}
	}
	
	/**
	 * Get the game board.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Get the player.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Get the computer player opponent.
	 */
	public ComputerPlayer getComputerPlayer() {
		return computer;
	}
	
	/**
	 * Get the current round.
	 */
	public int getRound() {
		return round;
	}
	
	/**
	 * Get the leaderboard.
	 */
	public Leaderboard getLeaderboard() {
		return leaderboard;
	}
	
	/**
	 * Get the settings.
	 */
	public SettingsManager getSettings() {
		return settings;
	}
}

import java.util.Random;

/**
 * Represents the Treasure Chase game mode.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.22032013
 *
 */
public class TreasureChase implements GameMode {
	
	private Player player;
	private Board board;
	private Leaderboard leaderboard;
	private int round;
	private boolean win;
	private SettingsManager settings;
	private ComputerPlayer computer;
	
	/**
	 * Construct a TreasureChase game with a specified player.
	 * 
	 * @param player The player to play the game.
	 * @param settings The settings to use for the game.
	 */
	public TreasureChase(Player player, SettingsManager settings) {
		this.player = player;
		this.leaderboard = new Leaderboard();
		this.win = false;
		this.settings = settings;
		
		// Generate a board with settings specified in the SettingsManager
		this.board = new Board(settings.getRows(), settings.getColumns());
		
		// Set a random tile on the board to contain treasure
		Random r = new Random();
		int rCol = r.nextInt(settings.getColumns()) + 1;
		int rRow = r.nextInt(settings.getRows()) + 1;
		
		this.board.getTile(rCol, rRow).setTreasure(true);
		
		// Initialise the computer opponent
		this.computer = new ComputerPlayer(this.board);
	}
	
	/**
	 * Move the token up a tile.
	 */
	public void moveTokenUp() {
		int newRow = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosRow == board.getHeight()) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getNorth() ||
					!board.getTile(tokenPosCol, 1).getSouth()) {
				// Can't pass through, failed
				return;
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
				return;
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(tokenPosCol, newRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
		
		player.setMoves(player.getMoves() + 1);
		round++;
		
		if(checkWin())
			win = true;
		
		computerMove();
	}
	
	/**
	 * Move the token down a tile.
	 */
	public void moveTokenDown() {
		int newRow = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosRow == 1) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getSouth() ||
					!board.getTile(tokenPosCol, board.getHeight()).getNorth()) {
				// Can't pass through, failed
				return;
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
				return;
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(tokenPosCol, newRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
		
		player.setMoves(player.getMoves() + 1);
		round++;
		
		if(checkWin())
			win = true;
		
		computerMove();
	}
	
	/**
	 * Move the token left a tile.
	 */
	public void moveTokenLeft() { 
		int newCol = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosCol == 1) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getWest() ||
					!board.getTile(board.getWidth(), tokenPosRow).getEast()) {
				// Can't pass through, failed
				return;
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
				return;
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(newCol, tokenPosRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
		
		player.setMoves(player.getMoves() + 1);
		round++;
		
		if(checkWin())
			win = true;
		
		computerMove();
	}
	
	/**
	 * Move the token right a tile.
	 */
	public void moveTokenRight() { 
		int newCol = 0;
		int tokenPosCol = board.getTokenPos()[0];
		int tokenPosRow = board.getTokenPos()[1];
		
		// Check if token will go over the board
		if(tokenPosCol == board.getWidth()) {
			// Make sure there isn't a wall blocking movement
			if(!board.getTile(tokenPosCol, tokenPosRow).getEast() ||
					!board.getTile(board.getWidth(), tokenPosRow).getWest()) {
				// Can't pass through, failed
				return;
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
				return;
			}
		}
		
		// Move token :)
		board.getTile(tokenPosCol, tokenPosRow).setToken(false);
		board.setTokenPos(newCol, tokenPosRow);
		board.getTile(board.getTokenPos()[0], board.getTokenPos()[1]).setToken(true);
		
		player.setMoves(player.getMoves() + 1);
		round++;
		
		if(checkWin())
			win = true;
		
		computerMove();
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
		
		player.setMoves(player.getMoves() + 1);
		round++;
		
		if(checkWin())
			win = true;
		
		computerMove();
	}
	
	/**
	 * Insert the spare tile into the specified row.
	 * 
	 * @param row The row to insert the spare tile into.
	 */
	public void insertRow(int row, Direction direc) throws IllegalMoveException {
		Tile newSpareTile = null;
		
		if(direc == Direction.LEFT)
			newSpareTile = insertTileRowLeft(row, player.getSpareTile());
		else if(direc == Direction.RIGHT)
			newSpareTile = insertTileRowRight(row, player.getSpareTile());
		else
			throw new IllegalMoveException("Invalid direction specified");
		
		if(newSpareTile != null) {
			// Success! New spare tile returned
			player.setSpareTile(newSpareTile);
			
			player.setMoves(player.getMoves() + 1);
			round++;
			
			if(checkWin())
				win = true;
			
			computerMove();
		}
		else {
			throw new IllegalMoveException("Could not retrieve new spare tile");
		}
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
			if(!board.getTile(i, row).isMovable()) {
				// Found an immovable tile
				throw new IllegalMoveException("Specified row contains one or more immovable tiles");
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
			if(!board.getTile(i, row).isMovable()) {
				// Found an immovable tile
				throw new IllegalMoveException("Specified row contains one or more immovable tiles");
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
	 */
	public void insertColumn(int column, Direction direc) throws IllegalMoveException {
		Tile newSpareTile = null;
		
		if(direc == Direction.BOTTOM)
			newSpareTile = insertTileColumnBottom(column, player.getSpareTile());
		else if(direc == Direction.TOP)
			newSpareTile = insertTileColumnTop(column, player.getSpareTile());
		else
			throw new IllegalMoveException("Invalid direction specified");
		
		if(newSpareTile != null) {
			// Success! New spare tile returned
			player.setSpareTile(newSpareTile);
			
			player.setMoves(player.getMoves() + 1);
			round++;
			
			if(checkWin())
				win = true;
			
			computerMove();
		}
		else {
			throw new IllegalMoveException("Could not retrieve new spare tile");
		}
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
			if(!board.getTile(column, i).isMovable()) {
				// Found an immovable tile
				throw new IllegalMoveException("Specified column contains one or more immovable tiles");
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
			if(!board.getTile(column, i).isMovable()) {
				// Found an immovable tile
				throw new IllegalMoveException("Specified column contains one or more immovable tiles");
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
	 * Save the current progress of the game.
	 */
	public void save() {
		return;
	}
	
	/**
	 * Check if the player has won the game.
	 */
	public boolean hasWon() {
		return win;
	}
	
	/**
	 * Check if the player has won by analysing the token position (i.e. if it contains treasure).
	 * 
	 * @return Whether or not the player has satisfied the win conditions.
	 */
	private boolean checkWin() {
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
		return;
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
}

/**
 * Represents the Treasure Chase game mode.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.11032013
 *
 */
public class TreasureChase implements GameMode {
	
	private Player player;
	private Board board;
	private Leaderboard leaderboard;
	private int round;
	private boolean win;
	
	/**
	 * Construct a TreasureChase game with a specified player.
	 * 
	 * @param player The player to play the game.
	 */
	public TreasureChase(Player player) {
		this.player = player;
		this.leaderboard = new Leaderboard();
		this.win = false;
		
		// Generate a board with default settings of 7x7
		this.board = new Board(7, 7);
	}
	
	/**
	 * Construct a TreasureChase game with a specified player and leaderboard.
	 * 
	 * @param player The player to play the game.
	 * @param leaderboard The leaderboard to use.
	 */
	public TreasureChase(Player player, Leaderboard leaderboard) {
		this(player);
		this.leaderboard = leaderboard;
	}
	
	public void moveToken(int row, int column) { return; }
	public void moveTile(int currRow, int currColumn, int newRow, int newColumn) { return; }
	
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
	}
	
	/**
	 * Rotate the tile specified by a particular angle.
	 * 
	 * @param column The column coordinate of the existing tile.
	 * @param row The row coordinate of the existing tile.
	 * @param angle The angle to rotate the spare tile by. Must be either 90, 180 or 270.
	 */
	public void rotateTile(int column, int row, int angle) throws NumberFormatException {
		// Check if tile is immovable/fixed
		if(!board.getTile(column, row).isMovable())
			return;
		
		if(angle < 0 || angle > 270)
			// Angle is out of bounds
			throw new NumberFormatException("Angle must either be 90, 180 or 270");
		
		if((angle % 90) != 0)
			// Angle isn't divisible by 90
			throw new NumberFormatException("Angle must either be 90, 180 or 270");
		
		Tile tile = board.getTile(column, row);
		int currAngle = RotationAngle.convertToInt(tile.getRotation());
		RotationAngle newAngle = RotationAngle.convertFromInt((angle + currAngle) % 360);
		tile.setRotation(newAngle);
		board.setTile(column, row, tile);
		
		player.setMoves(player.getMoves() + 1);
		round++;
	}
	
	/**
	 * Replace an existing tile on the board with the player's spare tile.
	 * 
	 * @param column The column coordinate of the existing tile.
	 * @param row The row coordinate of the existing tile.
	 */
	public void replaceTile(int column, int row) {
		Tile newSpareTile = replaceTile(column, row, player.getSpareTile());
		
		if(newSpareTile != null) {
			player.setSpareTile(newSpareTile);
		}
		else {
			System.out.println("Can't push spare tile here!");
		}
	}
	
	/**
	 * Replace an existing tile on the board with a new tile.
	 * 
	 * @param column The column coordinate of the existing tile.
	 * @param row The row coordinate of the existing tile.
	 * @param newTile The new tile to place over an existing tile.
	 * @return The tile that has been pushed off the board. Returns null on error.
	 */
	public Tile replaceTile(int column, int row, Tile newTile) {
		// TODO: Row and column insertion
		
		// Check if tile is immovable/fixed
		if(!board.getTile(column, row).isMovable())
			return null;
		
		Tile spareTile;
		boolean rowImmovable = false;
		boolean colImmovable = false;
		
		// Check if any immovable tiles reside in row
		for(int i = 1; i <= board.getWidth(); i++) {
			if(!board.getTile(i, row).isMovable())
				rowImmovable = true;
		}
		
		// Check if any immovable tiles reside in column
		for(int i = 1; i <= board.getHeight(); i++) {
			if(!board.getTile(column, i).isMovable())
				colImmovable = true;
		}
		
		if(!rowImmovable) {
			spareTile = board.getTile(board.getWidth(), row);
			
			for(int i = board.getWidth(); i > column; i--) {
				// Get the preceding tile and push it along
				Tile t = board.getTile(i - 1, row);
				board.setTile(i, row, t);
			}
		}
		else if(!colImmovable) {
			spareTile = board.getTile(column, board.getHeight());
			
			for(int i = board.getHeight(); i > row; i--) {
				// Get the preceding tile and push it along
				Tile t = board.getTile(column, i - 1);
				board.setTile(column, i, t);
			}
		}
		else {
			// Can't place a tile here
			return null;
		}
		
		board.setTile(column, row, newTile);
		
		player.setMoves(player.getMoves() + 1);
		round++;
		
		return spareTile;
	}
	
	/**
	 * Update the interface.
	 */
	public void update() {
		// Draw the board
		board.draw();
		
		// Draw the "HUB"
		Tile spareTile = player.getSpareTile();
		String[] tileRows = spareTile.getTileString();
		
		String roundString = Integer.toString(round);
		
		// Determine rounding padding
		if(round < 10)
			// Prepend 4 zeros
			roundString = "0000" + roundString;
		else if(round < 100)
			// Prepend 3 zeros
			roundString = "000" + roundString;
		else if(round < 1000)
			// Prepend 2 zeros
			roundString = "00" + roundString;
		
		// Score in Treasure Chase is rounds
		
		System.out.println();
		System.out.println("          --------------- ----------- -----------");
		System.out.println("         |  SPARE  TILE  |   ROUND   |   SCORE   |");
		System.out.println("         |===============|===========|===========|");
		System.out.println("         |    " + tileRows[0] + "    |           |           |");
		System.out.println("         |    " + tileRows[1] + "    |           |           |");
		System.out.println("         |    " + tileRows[2] + "    |   " + roundString + "   |   " + roundString + "   |");
		System.out.println("         |    " + tileRows[3] + "    |           |           |");
		System.out.println("         |    " + tileRows[4] + "    |           |           |");
		System.out.println("          --------------- ----------- -----------");
	}
	
	public void save() { return; }
	public void end() { return; }
	
	public int getRound() { return round; }
	public Player getPlayer() { return player; }
	
	/**
	 * Check if the player has won the game.
	 */
	public boolean hasWon() {
		return win;
	}
	
	/**
	 * Called in the event the player has won (i.e. completed the game mode objective(s)).
	 */
	public void onWin() {
		//
		// TODO: Clear console?
		//
		
		System.out.println("__   __             __                      _   _   _            _                                              \n" +
                           "\\ \\ / /            / _|                    | | | | | |          | |                                           \n" +
                           " \\ V /___  _   _  | |_ ___  _   _ _ __   __| | | |_| |__   ___  | |_ _ __ ___  __ _ ___ _   _ _ __ ___         \n" +
                           "  \\ // _ \\| | | | |  _/ _ \\| | | | '_ \\ / _` | | __| '_ \\ / _ \\ | __| '__/ _ \\/ _` / __| | | | '__/ _ \\ \n" +
                           "  | | (_) | |_| | | || (_) | |_| | | | | (_| | | |_| | | |  __/ | |_| | |  __/ (_| \\__ \\ |_| | | |  __/_ _ _  \n" +
                           "  \\_/\\___/ \\__,_| |_| \\___/ \\__,_|_| |_|\\__,_|  \\__|_| |_|\\___|  \\__|_|  \\___|\\__,_|___/\\__,_|_|  \\___(_|_|_) \n");
                                                                                                             
                                                                                                             
		
		// Wait 2 seconds before final congratulation message
		try { Thread.sleep(2000); } catch(InterruptedException e) {}
		
		System.out.println("     _____ _____ _   _ _____ ______  ___ _____ _   _ _       ___ _____ _____ _____ _   _  _____ _ _           \n" +
                           "    /  __ \\  _  | \\ | |  __ \\| ___ \\/ _ \\_   _| | | | |     / _ \\_   _|_   _|  _  | \\ | |/  ___| | |   \n" +
                           "    | /  \\/ | | |  \\| | |  \\/| |_/ / /_\\ \\| | | | | | |    / /_\\ \\| |   | | | | | |  \\| |\\ `--.| | | \n" +
                           "    | |   | | | | . ` | | __ |    /|  _  || | | | | | |    |  _  || |   | | | | | | . ` | `--. \\ | |         \n" +
                           "    | \\__/\\ \\_/ / |\\  | |_\\ \\| |\\ \\| | | || | | |_| | |____| | | || |  _| |_\\ \\_/ / |\\  |/\\__/ /_|_| \n" +
                           "     \\____/\\___/\\_| \\_/\\____/\\_| \\_\\_| |_/\\_/  \\___/\\_____/\\_| |_/\\_/  \\___/ \\___/\\_| \\_/\\____/(_|_)  \n");
                                                                                                
                                                                                                
		
		//
		// TODO: Display leaderboard and do high score checking...
		//
	}
	
}

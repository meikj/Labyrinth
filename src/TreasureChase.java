/**
 * Represents the TreasureChase game mode
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.07032013
 *
 */
public class TreasureChase implements GameMode {
	
	private Player player;
	private Board board;
	private Leaderboard leaderboard;
	private int round;
	
	/**
	 * Construct a TreasureChase game with a specified player
	 * 
	 * @param player The player to play the game
	 */
	public TreasureChase(Player player) {
		this.player = player;
		this.leaderboard = new Leaderboard();
		
		// Generate a board with default settings of 7x7
		this.board = new Board(7, 7);
	}
	
	/**
	 * Construct a TreasureChase game with a specified player and leaderboard
	 * 
	 * @param player The player to play the game
	 * @param leaderboard The leaderboard to use
	 */
	public TreasureChase(Player player, Leaderboard leaderboard) {
		this(player);
		this.leaderboard = leaderboard;
	}
	
	public void moveToken(int row, int column) { return; }
	public void moveTile(int currRow, int currColumn, int newRow, int newColumn) { return; }
	
	/**
	 * Rotate the spare tile to a particular rotation
	 * 
	 * @param angle The angle to rotate the spare tile by
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
	 * Rotate the tile specified to a particular rotation
	 * 
	 * @param column The column coordinate of the existing tile
	 * @param row The row coordinate of the existing tile
	 * @param angle The angle to rotate the tile by
	 */
	public void rotateTile(int column, int row, int angle) throws NumberFormatException {
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
	 * Replace an existing tile on the board with the Player's spare tile
	 * 
	 * @param column The column coordinate of the existing tile
	 * @param row The row coordinate of the existing tile
	 */
	public void replaceTile(int column, int row) {
		Tile oldTile = board.getTile(column, row);
		
		board.setTile(column, row, player.getSpareTile());
		player.setSpareTile(oldTile);
		
		player.setMoves(player.getMoves() + 1);
		round++;
	}
	
	/**
	 * Replace an existing tile on the board with a new tile
	 * 
	 * @param column The column coordinate of the existing tile
	 * @param row The row coordinate of the existing tile
	 * @param newTile The new tile to place over an existing tile
	 */
	public void replaceTile(int column, int row, Tile newTile) {
		board.setTile(column, row, newTile);
		
		player.setMoves(player.getMoves() + 1);
		round++;
	}
	
	/**
	 * Update the interface
	 */
	public void update() {
		// Draw the board
		board.draw();
		
		// Draw the "HUB"
		Tile spareTile = player.getSpareTile();
		String spareTileStr = spareTile.getTileString();
		String[] tileRows = spareTileStr.split("\n");
		
		String roundString = Integer.toString(round);
		
		// Determine rounding padding
		if(round < 10)
			// Prepend 2 zeros
			roundString = "00" + roundString;
		else if(round < 100)
			// Prepend 1 zero
			roundString = "0" + roundString;
		
		// Score in Treasure Chase is rounds
		
		System.out.println(" ------------- ------- -------");
		System.out.println("| SPARE  TILE | ROUND | SCORE |");
		System.out.println("|=============|=======|=======|");
		System.out.println("|     " + tileRows[0] + "     |       |       |");
		System.out.println("|     " + tileRows[1] + "     |  " + roundString + "  |  " + roundString + "  |");
		System.out.println("|     " + tileRows[2] + "     |       |       |");
		System.out.println(" ------------- ------- -------");
	}
	
	public void save() { return; }
	public void end() { return; }
	
	public int getRound() { return round; }
	public Player getPlayer() { return player; }
	
}

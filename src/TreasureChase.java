/**
 * Represents the TreasureChase game mode
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.06032013
 *
 */
public class TreasureChase {
	
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
		
		// Generate the board with random treasure placements, etc.
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
	
	public void moveToken(int x, int y) { return; }
	public void moveTile(int currX, int currY, int newX, int newY) { return; }
	
	/**
	 * Rotate the tile specified to a particular rotation
	 * 
	 * @param x The x coordinate of the existing tile
	 * @param y The y coordinate of the existing tile
	 * @param newAngle The new angle of the tile
	 */
	public void rotateTile(int x, int y, RotationAngle newAngle) {
		Tile tile = board.getTile(x, y);
		tile.setRotation(newAngle);
		board.setTile(x, y, tile);
		player.setMoves(player.getMoves() + 1);
		round++;
	}
	
	/**
	 * Replace an existing tile on the board with the Player's spare tile
	 * 
	 * @param x The x coordinate of the existing tile
	 * @param y The y coordinate of the existing tile
	 */
	public void replaceTile(int x, int y) {
		Tile oldTile = board.getTile(x, y);
		
		board.setTile(x, y, player.getSpareTile());
		player.setSpareTile(oldTile);
		player.setMoves(player.getMoves() + 1);
		round++;
	}
	
	/**
	 * Replace an existing tile on the board with a new tile
	 * 
	 * @param x The x coordinate of the existing tile
	 * @param y The y coordinate of the existing tile
	 * @param newTile The new tile to place over an existing tile
	 */
	public void replaceTile(int x, int y, Tile newTile) {
		board.setTile(x, y, newTile);
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
	
	/**
	 * Get tile from board
	 * @param x The x coordinate of tile
	 * @param y The y coordinate of tile
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return board.getTile(x, y);
	}
	
}

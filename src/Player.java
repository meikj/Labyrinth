import java.util.Random;

/**
 * Represents a player.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.24032013
 *
 */
public class Player {
	
	private int score;
	private int moves;
	private Tile spareTile;
	private String lastMove;
	
	/**
	 * Construct a player object with a random spare tile.
	 */
	public Player() {
		this.score = 0;
		this.moves = 0;
		this.lastMove = "N/A";
		
		// Generate a random spare tile
		Random r = new Random();
		int randomSelection = r.nextInt(4); // CORNER, LINE, TSHAPE, CROSS
		int rotation = r.nextInt(4); // DEFAULT, NINETY, HUNDREDANDEIGHTY, TWOHUNDREDANDSEVENTY
		
		TileType[] tileTypes = TileType.values();
		RotationAngle[] rotationAngles = RotationAngle.values();
		
		this.spareTile = new Tile(tileTypes[randomSelection], rotationAngles[rotation]);
	}
	
	/**
	 * Set the current score of the player.
	 */
	public void setScore(int newScore) {
		score = newScore;
	}
	
	/**
	 * Set the current number of moves the player has made.
	 */
	public void setMoves(int newMoves) {
		moves = newMoves;
	}
	
	/**
	 * Set the current spare tile of the player.
	 * 
	 * @param newTile The new spare tile.
	 */
	public void setSpareTile(Tile newTile) {
		spareTile = newTile;
	}
	
	/**
	 * Get the current score of the player.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Get the current number of moves the player has made.
	 */
	public int getMoves() {
		return moves;
	}
	
	/**
	 * Get the current spare tile of the player.
	 */
	public Tile getSpareTile() {
		return spareTile;
	}
	
	/**
	 * Get the last move made by this computer player.
	 */
	public String getLastMove() {
		return lastMove;
	}
	
	/**
	 * Update the last move string.
	 * 
	 * @param s The last move performed.
	 */
	public void updateLastMove(String s) {
		lastMove = s;
	}

}

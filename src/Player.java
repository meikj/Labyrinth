import java.util.Random;

/**
 * Represents the player in the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.22032013
 *
 */
public class Player {
	
	private int score;
	private int moves;
	private Tile spareTile;
	private String lastMove;
	
	/**
	 * Construct a player object.
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
	
	public void setScore(int newScore) { score = newScore; }
	public void setMoves(int newMoves) { moves = newMoves; }
	public void setSpareTile(Tile newTile) { spareTile = newTile; }
	
	public int getScore() { return score; }
	public int getMoves() { return moves; }
	public Tile getSpareTile() { return spareTile; }
	
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

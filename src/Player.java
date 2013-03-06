/**
 * Represents the player in the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
 *
 */
public class Player {
	
	private int score;
	private int moves;
	private Tile spareTile;
	
	/**
	 * Construct a player object
	 */
	public Player() {
		this.score = 0;
		this.moves = 0;
		this.spareTile = new Tile(TileType.TSHAPE);
	}
	
	public void setScore(int newScore) { score = newScore; }
	public void setMoves(int newMoves) { moves = newMoves; }
	public void setSpareTile(Tile newTile) { spareTile = newTile; }
	
	public int getScore() { return score; }
	public int getMoves() { return moves; }
	public Tile getSpareTile() { return spareTile; }

}

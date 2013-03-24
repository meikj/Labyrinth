import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a computer opponent for a player to play against in the Labyrinth
 * game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.24032013
 *
 */
public class ComputerPlayer extends Player {
	
	private ArrayList<Integer> columns;
	private ArrayList<Integer> rows;
	private Random rand;
	
	// Save RotationAngle and TileType arrays for "caching" purposes
	private RotationAngle[] angles;
	private TileType[] types;
	
	/**
	 * Generate a computer player opponent using the board
	 * settings as information on the computers tile moves.
	 * 
	 * @param board The board to gather information from.
	 */
	public ComputerPlayer(Board board) {
		this.columns = new ArrayList<Integer>();
		this.rows = new ArrayList<Integer>();
		this.rand = new Random();
		
		// Add available rows and columns (odd tiles are immovable, so get even)
		for(int i = 1; i <= board.getHeight(); i++) {
			if(i % 2 == 0) {
				rows.add(i);
			}
		}
		for(int i = 1; i <= board.getWidth(); i++) {
			if(i % 2 == 0) {
				columns.add(i);
			}
		}
		
		angles = RotationAngle.values();
		types = TileType.values();
	}
	
	/**
	 * Get a random column coordinate from the available rows.
	 */
	public Integer getRandomColumn() {
		int r = rand.nextInt(columns.size());
		return columns.get(r);
	}
	
	/**
	 * Get a random row coordinate from the available rows.
	 */
	public Integer getRandomRow() {
		int r = rand.nextInt(rows.size());
		return rows.get(r);
	}
	
	/**
	 * Get a random tile rotation.
	 */
	public RotationAngle getRandomRotation() {
		int r = rand.nextInt(angles.length);
		return angles[r];
	}
	
	/**
	 * Get a random tile type.
	 */
	public TileType getRandomTileType() {
		int r = rand.nextInt(types.length);
		return types[r];
	}

}

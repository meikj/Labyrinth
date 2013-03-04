/**
 * Represents a tile for use within a board
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
 *
 */
public class Tile {
	
	private TileType type;
	private RotationAngle rotation;
	private boolean treasure;
	
	/**
	 * Construct a tile of a particular type
	 * 
	 * @param type The type of tile to construct
	 */
	public Tile(TileType type) {
		this.type = type;
		this.rotation = RotationAngle.DEFAULT;
	}
	
	/**
	 * Get the tile type
	 */
	public TileType getType() {
		return type;
	}
	
	/**
	 * Set the tile type
	 */
	public void setType(TileType newType) {
		this.type = newType;
	}
	
	/**
	 * Check whether or not the tile contains treasure
	 */
	public boolean hasTreasure() {
		return treasure;
	}
	
	/**
	 * Set whether or not the tile contains treasure
	 */
	public void setTreasure(boolean state) {
		this.treasure = state;
	}
	
	/**
	 * Get the rotation of the tile
	 */
	public RotationAngle getRotation() {
		return rotation;
	}
	
	/**
	 * Set the rotation of the tile
	 */
	public void setRotation(RotationAngle newRotation) {
		this.rotation = newRotation;
	}

}

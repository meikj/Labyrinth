/**
 * Represents a tile for use within a board
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.06032013
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
	 * Construct a tile of a particular type and rotation
	 * 
	 * @param type The type of tile to construct
	 * @param rotation The rotation of the tile to construct
	 */
	public Tile(TileType type, RotationAngle rotation) {
		this(type);
		this.rotation = rotation;
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
		System.out.println("[DEBUG]: setRotation = " + newRotation);
		this.rotation = newRotation;
		System.out.println(getTileString());
	}
	
	public String getTileString() {
		switch(type) {
			case CORNER:
				// Return corner string
				//    
				//  --
				//  |
				
				if(rotation == RotationAngle.DEFAULT)
					return "   \n --\n | \n";
				else if(rotation == RotationAngle.NINETY)
					return "   \n-- \n | \n";
				else if(rotation == RotationAngle.HUNDREDANDEIGHTY)
					return " | \n | \n-- \n";
				else
					return " | \n | \n --\n";
			case LINE:
				// Return line string
				// | 
				// | 
				// | 
				
				if(rotation == RotationAngle.DEFAULT)
					return " | \n | \n | \n";
				else if(rotation == RotationAngle.NINETY)
					return "   \n---\n   ";
				else if(rotation == RotationAngle.HUNDREDANDEIGHTY)
					return " | \n | \n | \n";
				else
					return "   \n---\n   ";
			case TSHAPE:
				// Return tshape string:
				//  |  
				//  |-
				//  |
				
				if(rotation == RotationAngle.DEFAULT)
					return " | \n |-\n | \n";
				else if(rotation == RotationAngle.NINETY)
					return "   \n---\n | \n";
				else if(rotation == RotationAngle.HUNDREDANDEIGHTY)
					return " | \n-| \n | \n";
				else
					return " | \n | \n---\n";
			case CROSS:
				// Return cross string
				return " | \n-+-\n | \n";
			case EMPTY:
				// Return empty string
				return "   \n   \n   \n";
			default:
				// Throw error?
				break;
		}
		
		return null;
	}

}

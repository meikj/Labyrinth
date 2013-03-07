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
	private boolean token;
	
	/**
	 * Construct a tile of a particular type
	 * 
	 * @param type The type of tile to construct
	 */
	public Tile(TileType type) {
		this.type = type;
		this.rotation = RotationAngle.DEFAULT;
		this.treasure = false;
		this.token = false;
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
		this.rotation = newRotation;
	}
	
	/**
	 * Check whether or not the tile contains the token
	 */
	public boolean hasToken() {
		return token;
	}
	
	/**
	 * Set whether or not the tile contains the token
	 */
	public void setToken(boolean state) {
		this.token = state;
	}
	
	/**
	 * Get the String representation of the Tile. A tile consists of 3x3 characters
	 * separated by new lines (e.g. "   \n   \n   \n"). This allows the tile to
	 * be split up into 3 sections (top, middle and bottom) for easier manipulation
	 * within an interface.
	 * 
	 * @return The String representation of the Tile
	 */
	public String getTileString() {
		if(hasToken())
			return "   \n O \n   \n";
		
		if(hasTreasure())
			return " - \n|T|\n - \n";
		
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
					return " | \n-- \n   \n";
				else
					return " | \n --\n   \n";
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
					return " | \n---\n   \n";
			case CROSS:
				// Return cross string
				return " | \n-+-\n | \n";
			case EMPTY:
				// Return empty string
				return "   \n   \n   \n";
			default:
				// Just return an empty string
				return "   \n   \n   \n";
		}
	}

}

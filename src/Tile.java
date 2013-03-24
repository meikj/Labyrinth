/**
 * Represents a tile for use within a board.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.24032013
 *
 */
public class Tile {
	
	private TileType type;
	private RotationAngle rotation;
	private boolean treasure;
	private boolean token;
	private boolean movable;
	private String[] tileString;
	
	// Entry points
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	// Tile style
	private static final Character wallChar = '\u2588'; // Solid block
	private static final Character immovableChar = '\u2592'; // Light black block
	private static final Character tokenChar = 'O';
	private static final Character treasureChar = 'T';
	
	/**
	 * Construct a tile of a particular type.
	 * 
	 * @param type The type of tile to construct.
	 */
	public Tile(TileType type) {
		this.type = type;
		this.rotation = RotationAngle.DEFAULT;
		this.treasure = false;
		this.token = false;
		this.movable = true;
		this.tileString = formTileString();
	}
	
	/**
	 * Construct a tile of a particular type and rotation.
	 * 
	 * @param type The type of tile to construct.
	 * @param rotation The rotation of the tile to construct.
	 */
	public Tile(TileType type, RotationAngle rotation) {
		this(type);
		this.rotation = rotation;
		this.tileString = formTileString();
	}
	
	public Tile(TileType type, RotationAngle rotation, boolean movable) {
		this(type, rotation);
		this.movable = movable;
		this.tileString = formTileString();
	}
	
	/**
	 * Get the tile type.
	 */
	public TileType getType() {
		return type;
	}
	
	/**
	 * Set the tile type.
	 */
	public void setType(TileType newType) {
		type = newType;
		
		// Type has been altered, reform tile string
		tileString = formTileString();
	}
	
	/**
	 * Check whether or not the tile contains treasure.
	 */
	public boolean hasTreasure() {
		return treasure;
	}
	
	/**
	 * Set whether or not the tile contains treasure.
	 */
	public void setTreasure(boolean state) {
		treasure = state;
		
		// Treasure has been altered, re-form the tile string
		tileString = formTileString();
	}
	
	/**
	 * Get the rotation of the tile.
	 */
	public RotationAngle getRotation() {
		return rotation;
	}
	
	/**
	 * Set the rotation of the tile.
	 */
	public void setRotation(RotationAngle newRotation) {
		if(!isMovable())
			return;
		
		rotation = newRotation;
		
		// Rotation has been altered, reform the tile string
		tileString = formTileString();
	}
	
	/**
	 * Check whether or not the tile contains the token.
	 */
	public boolean hasToken() {
		return token;
	}
	
	/**
	 * Set whether or not the tile contains the token.
	 */
	public void setToken(boolean state) {
		token = state;
		
		// Token has been altered, reform the tile string
		tileString = formTileString();
	}
	
	/**
	 * Check whether or not the tile is movable.
	 */
	public boolean isMovable() {
		return movable;
	}
	
	/**
	 * Set whether or not the tile is movable.
	 */
	public void setMovable(boolean state) {
		movable = state;
		
		// Reform tile string to reflect new state
		tileString = formTileString();
	}
	
	/**
	 * Get the string representation of the tile in the form of an array.
	 * 
	 * @return The string array formation of the tile split up into top, top-middle,
	 * middle, middle-bottom and bottom respectively.
	 */
	public String[] getTileString() {
		return tileString;
	}
	
	/**
	 * Convert a tile pattern to its final string representation.
	 * 
	 * @param pattern The tile pattern. For example, a cross:
	 * 
	 * pattern[0] = "1100011"
	 * pattern[1] = "1100011"
	 * pattern[2] = "0000000"
	 * pattern[3] = "1100011"
	 * pattern[4] = "1100011"
	 * 
	 * @return The converted string equivalent.
	 */
	private String[] convertFromPattern(String[] pattern) {
		for(int i = 0; i < pattern.length; i++) {
			char[] line = pattern[i].toCharArray();
			
			for(int j = 0; j < line.length; j++) {
				if(line[j] == '1') {
					// Replace with wall
					line[j] = wallChar;
				} else if(line[j] == '0') {
					// Replace with space
					line[j] = ' ';
				}
			}
			
			// Update line
			pattern[i] = new String(line);
		}
		
		return pattern;
	}
	
	/**
	 * Form the tile string array in accordance to the tile type.
	 * 
	 * @return The string array formation of the tile split up into top, top-middle,
	 * middle, middle-bottom and bottom respectively.
	 */
	private String[] formTileString() {
		String[] tile = new String[5];
		
		switch(type) {
		case CROSS:
			tile[0] = "1100011";
			tile[1] = "1100011";
			tile[2] = "0000000";
			tile[3] = "1100011";
			tile[4] = "1100011";
			
			// Set entry points
			north = true;
			east = true;
			south = true;
			west = true;
			
			break;
		case TSHAPE:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "1100000";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = true;
				east = true;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "1111111";
				tile[1] = "1111111";
				tile[2] = "0000000";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = false;
				east = true;
				south = true;
				west = true;
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "0000011";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = true;
				east = false;
				south = true;
				west = true;
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "0000000";
				tile[3] = "1111111";
				tile[4] = "1111111";
				
				// Set entry points
				north = true;
				east = true;
				south = false;
				west = true;
			}
			
			break;
		case LINE:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "1100011";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = true;
				east = false;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "1111111";
				tile[1] = "1111111";
				tile[2] = "0000000";
				tile[3] = "1111111";
				tile[4] = "1111111";
				
				// Set entry points
				north = false;
				east = true;
				south = false;
				west = true;
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "1100011";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = true;
				east = false;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "1111111";
				tile[1] = "1111111";
				tile[2] = "0000000";
				tile[3] = "1111111";
				tile[4] = "1111111";
				
				// Set entry points
				north = false;
				east = true;
				south = false;
				west = true;
			}
			
			break;
		case CORNER:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "1111111";
				tile[1] = "1111111";
				tile[2] = "1100000";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = false;
				east = true;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "1111111";
				tile[1] = "1111111";
				tile[2] = "0000011";
				tile[3] = "1100011";
				tile[4] = "1100011";
				
				// Set entry points
				north = false;
				east = false;
				south = true;
				west = true;
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "0000011";
				tile[3] = "1111111";
				tile[4] = "1111111";
				
				// Set entry points
				north = true;
				east = false;
				south = false;
				west = true;
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "1100011";
				tile[1] = "1100011";
				tile[2] = "1100000";
				tile[3] = "1111111";
				tile[4] = "1111111";
				
				// Set entry points
				north = true;
				east = true;
				south = false;
				west = false;
			}
			
			break;
		case EMPTY:
			tile[0] = "0000000";
			tile[1] = "0000000";
			tile[2] = "0000000";
			tile[3] = "0000000";
			tile[4] = "0000000";
			
			// Set entry points
			north = true;
			east = true;
			south = true;
			west = true;
			
			break;
		}
		
		// Convert pattern to appropriate characters
		tile = convertFromPattern(tile);
		
		// Check if tile contains treasure or token
		if(hasToken()) {
			// Change middle section, middle element to token
			char[] tileMiddle = tile[2].toCharArray();
			tileMiddle[3] = tokenChar; // Set to token
			tile[2] = new String(tileMiddle);
		}
		else if(hasTreasure()) {
			// Change middle section, middle element to treasure
			char[] tileMiddle = tile[2].toCharArray();
			tileMiddle[3] = treasureChar; // Set to token
			tile[2] = new String(tileMiddle);
		}
		
		// Check if tile is immovable
		if(!isMovable()) {
			// Change tile string to fixed representation
			for(int i = 0; i < 5; i++)
				tile[i] = tile[i].replace(' ', immovableChar);
		}
		
		return tile;
	}
	
	/**
	 * Check whether or not the north access point is available.
	 */
	public boolean getNorth() {
		return north;
	}
	
	/**
	 * Check whether or not the north access point is available.
	 */
	public boolean getEast() {
		return east;
	}
	
	/**
	 * Check whether or not the north access point is available.
	 */
	public boolean getSouth() {
		return south;
	}
	
	/**
	 * Check whether or not the north access point is available.
	 */
	public boolean getWest() {
		return west;
	}

}

/**
 * Represents a tile for use within a board.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.11032013
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
		this.type = newType;
		
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
		this.treasure = state;
		
		// Treasure has been altered, re-form the tile string
		this.tileString = formTileString();
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
	 * Form the tile string array in accordance to the tile type.
	 * 
	 * @return The string array formation of the tile split up into top, top-middle,
	 * middle, middle-bottom and bottom respectively.
	 */
	private String[] formTileString() {
		String[] tile = new String[5];
		
		switch(type) {
		case CROSS:
			tile[0] = "██   ██";
			tile[1] = "██   ██";
			tile[2] = "       ";
			tile[3] = "██   ██";
			tile[4] = "██   ██";
			
			// Set entry points
			north = true;
			east = true;
			south = true;
			west = true;
			
			break;
		case TSHAPE:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "██     ";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = true;
				east = true;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "███████";
				tile[1] = "███████";
				tile[2] = "       ";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = false;
				east = true;
				south = true;
				west = true;
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "     ██";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = true;
				east = false;
				south = true;
				west = true;
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "       ";
				tile[3] = "███████";
				tile[4] = "███████";
				
				// Set entry points
				north = true;
				east = true;
				south = false;
				west = true;
			}
			
			break;
		case LINE:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "██   ██";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = true;
				east = false;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "███████";
				tile[1] = "███████";
				tile[2] = "       ";
				tile[3] = "███████";
				tile[4] = "███████";
				
				// Set entry points
				north = false;
				east = true;
				south = false;
				west = true;
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "██   ██";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = true;
				east = false;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "███████";
				tile[1] = "███████";
				tile[2] = "       ";
				tile[3] = "███████";
				tile[4] = "███████";
				
				// Set entry points
				north = false;
				east = true;
				south = false;
				west = true;
			}
			
			break;
		case CORNER:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "███████";
				tile[1] = "███████";
				tile[2] = "██     ";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = false;
				east = true;
				south = true;
				west = false;
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "███████";
				tile[1] = "███████";
				tile[2] = "     ██";
				tile[3] = "██   ██";
				tile[4] = "██   ██";
				
				// Set entry points
				north = false;
				east = false;
				south = true;
				west = true;
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "     ██";
				tile[3] = "███████";
				tile[4] = "███████";
				
				// Set entry points
				north = true;
				east = false;
				south = false;
				west = true;
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "██   ██";
				tile[1] = "██   ██";
				tile[2] = "██     ";
				tile[3] = "███████";
				tile[4] = "███████";
				
				// Set entry points
				north = true;
				east = true;
				south = false;
				west = false;
			}
			
			break;
		case EMPTY:
			tile[0] = "     ";
			tile[1] = "     ";
			tile[2] = "     ";
			tile[3] = "     ";
			tile[4] = "     ";
			
			// Set entry points
			north = true;
			east = true;
			south = true;
			west = true;
			
			break;
		}
		
		// Check if tile contains treasure or token
		if(hasToken()) {
			// Change middle section, middle element to token
			char[] tileMiddle = tile[2].toCharArray();
			tileMiddle[3] = 'O'; // Set to token
			tile[2] = new String(tileMiddle);
		}
		else if(hasTreasure()) {
			// Change middle section, middle element to treasure
			char[] tileMiddle = tile[2].toCharArray();
			tileMiddle[3] = 'T'; // Set to token
			tile[2] = new String(tileMiddle);
		}
		
		// Check if tile is immovable
		if(!isMovable()) {
			// Change tile string to fixed representation
			for(int i = 0; i < 5; i++)
				tile[i] = tile[i].replace(' ', '▒');
		}
		
		return tile;
	}
	
	public boolean getNorth() { return north; }
	public boolean getEast() { return east; }
	public boolean getSouth() { return south; }
	public boolean getWest() { return west; }

}

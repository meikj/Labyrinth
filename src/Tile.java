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
	private String[] tileString;
	
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
		this.tileString = formTileString();
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
		this.tileString = formTileString();
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
		
		// Treasure has been altered, re-form the tile string
		this.tileString = formTileString();
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
		
		// Rotation has been altered, re-form the tile string
		this.tileString = formTileString();
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
		
		// Token has been altered, re-form the tile string
		this.tileString = formTileString();
	}
	
	/**
	 * Get the String representation of the Tile. A tile consists of 5x5 characters.
	 * The tile is separated into 5 sections (top, top-middle, middle, middle-bottom, bottom).
	 * As such, a String array of 5 elements is returned containing the aforementioned
	 * sections.
	 * 
	 * @return The String array of the Tile
	 */
	public String[] getTileString() {
		return tileString;
	}
	
	/**
	 * Form a tile string in accordance to its current type and rotation.
	 * This forms a String array, consisting of 5 sections: top, top-middle, middle,
	 * middle-bottom, and bottom.
	 * 
	 * @return The String array of the Tile
	 */
	private String[] formTileString() {
		String[] tile = new String[5];
		
		switch(type) {
		case CROSS:
			tile[0] = " | | ";
			tile[1] = "—   —";
			tile[2] = "     ";
			tile[3] = "—   —";
			tile[4] = " | | ";
			
			break;
		case TSHAPE:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = " | | ";
				tile[1] = " |  —";
				tile[2] = " |   ";
				tile[3] = " |  —";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "     ";
				tile[1] = "—————";
				tile[2] = "     ";
				tile[3] = "—   —";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = " | | ";
				tile[1] = "—  | ";
				tile[2] = "   | ";
				tile[3] = "—  | ";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = " | | ";
				tile[1] = "—   —";
				tile[2] = "     ";
				tile[3] = "—————";
				tile[4] = "     ";
			}
			
			break;
		case LINE:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = " | | ";
				tile[1] = " | | ";
				tile[2] = " | | ";
				tile[3] = " | | ";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "     ";
				tile[1] = "—————";
				tile[2] = "     ";
				tile[3] = "—————";
				tile[4] = "     ";
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = " | | ";
				tile[1] = " | | ";
				tile[2] = " | | ";
				tile[3] = " | | ";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = "     ";
				tile[1] = "—————";
				tile[2] = "     ";
				tile[3] = "—————";
				tile[4] = "     ";
			}
			
			break;
		case CORNER:
			if(rotation == RotationAngle.DEFAULT) {
				tile[0] = "     ";
				tile[1] = "  ———";
				tile[2] = " |   ";
				tile[3] = " |  —";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.NINETY) {
				tile[0] = "     ";
				tile[1] = "———  ";
				tile[2] = "   | ";
				tile[3] = "—  | ";
				tile[4] = " | | ";
			} else if(rotation == RotationAngle.HUNDREDANDEIGHTY) {
				tile[0] = " | | ";
				tile[1] = "-  | ";
				tile[2] = "   | ";
				tile[3] = "———  ";
				tile[4] = "     ";
			} else if(rotation == RotationAngle.TWOHUNDREDANDSEVENTY) {
				tile[0] = " | | ";
				tile[1] = " |  —";
				tile[2] = " |   ";
				tile[3] = "  ———";
				tile[4] = "     ";
			}
			
			break;
		case EMPTY:
			tile[0] = "     ";
			tile[1] = "     ";
			tile[2] = "     ";
			tile[3] = "     ";
			tile[4] = "     ";
			
			break;
		}
		
		// Check if tile contains treasure or token
		if(hasToken()) {
			// Change middle section, middle element to token
			char[] tileMiddle = tile[2].toCharArray();
			tileMiddle[2] = 'O'; // Set to token
			tile[2] = new String(tileMiddle);
		}
		else if(hasTreasure()) {
			// Change middle section, middle element to treasure
			char[] tileMiddle = tile[2].toCharArray();
			tileMiddle[2] = 'T'; // Set to token
			tile[2] = new String(tileMiddle);
		}
		
		return tile;
	}

}

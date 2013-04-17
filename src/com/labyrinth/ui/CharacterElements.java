package com.labyrinth.ui;

import java.nio.charset.Charset;

/**
 * Represents all the available character elements for use within
 * the text-based user interface. The characters are initialised in
 * relation to the in use character set so as to ensure compatibility.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.17032013
 *
 */
public class CharacterElements {
	
	// Store the charset in use
	public static final String charSet = Charset.defaultCharset().toString().toLowerCase();
	
	// Static characters
	public static Character charBlock;
	public static Character charImmovable;
	public static Character charToken;
	public static Character charTreasure;
	public static Character charBorderVertical;
	public static Character charBorderHorizontal;
	public static Character charBorderCornerTopLeft;
	public static Character charBorderCornerTopRight;
	public static Character charBorderCornerBottomLeft;
	public static Character charBorderCornerBottomRight;
	
	/**
	 * Set the static characters in accordance to the character set.
	 */
	public static void initCharacters() { 
		// Initialise the tile characters in accordance to the charset
		if(charSet.contains("utf")) {
			// UTF charset available
			charBlock = '\u2588'; // Solid block
			charImmovable = '\u2592'; // Light black block
			charToken = 'O';
			charTreasure = 'T';
			charBorderVertical = '\u2551';
			charBorderHorizontal = '\u2550';
			charBorderCornerTopLeft = '\u2554';
			charBorderCornerTopRight = '\u2557';
			charBorderCornerBottomLeft = '\u255A';
			charBorderCornerBottomRight = '\u255D';
		} else {
			// Most likely ASCII on Windows CMD
			charBlock = (char) 219; // Solid block
			charImmovable = (char) 177; // Light black block
			charToken = 'O';
			charTreasure = 'T';
			charBorderVertical = (char) 186;
			charBorderHorizontal = (char) 205;
			charBorderCornerTopLeft = (char) 201;
			charBorderCornerTopRight = (char) 187;
			charBorderCornerBottomLeft = (char) 200;
			charBorderCornerBottomRight = (char) 188;
		}
	}

}

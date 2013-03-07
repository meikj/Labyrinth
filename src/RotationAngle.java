/**
 * Used to represent the choice of possible rotation angles for use with tile
 * manipulation
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
 *
 */
public enum RotationAngle {
	
	DEFAULT, NINETY, HUNDREDANDEIGHTY, TWOHUNDREDANDSEVENTY;
	
	public static int getAsInt(RotationAngle ra) {
		switch(ra) {
			case DEFAULT:
				return 0;
			case NINETY:
				return 90;
			case HUNDREDANDEIGHTY:
				return 180;
			case TWOHUNDREDANDSEVENTY:
				return 270;
			default:
					return 0;
		}
	}
	
	public static RotationAngle convertFromInt(int angle) {
		if(angle == 90)
			return NINETY;
		else if(angle == 180)
			return HUNDREDANDEIGHTY;
		else if(angle == 270)
			return TWOHUNDREDANDSEVENTY;
		else
			return DEFAULT;
	}

}

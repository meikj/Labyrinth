import java.util.Scanner;

/**
 * The main entry point to the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.06032013
 *
 */
public class Labyrinth {
	
	public static void main(String[] args) {
		TreasureChase tc = new TreasureChase(new Player());
		
		// Create a new tile to test later user tile entry
		 
		Tile newTile = new Tile(TileType.TSHAPE);
		newTile.setRotation(RotationAngle.NINETY);
		 
		System.out.println("Labyrinth game!\n");
		 
		Scanner sc = new Scanner(System.in);
		 
		// Initialise player's spare tile
		Tile spareTile = new Tile(TileType.TSHAPE);
		spareTile.setRotation(RotationAngle.HUNDREDANDEIGHTY);
		 
		tc.update();
		 
		System.out.println("\nPlease enter your spare tile at coordinates <x> <y>.");
		 
		String input = sc.nextLine();
		 
		String[] coordinates = input.split(" ");
		 
		int x = Integer.parseInt(coordinates[0]);
		int y = Integer.parseInt(coordinates[1]);
		 
		tc.replaceTile(x, y, spareTile);
		tc.update();
	}

}

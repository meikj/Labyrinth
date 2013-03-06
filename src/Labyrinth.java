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
		System.out.println("Labyrinth game!");
		
		Tile spareTile = new Tile(TileType.TSHAPE);
		spareTile.setRotation(RotationAngle.NINETY);
		
		Board board = new Board(7, 7);
		board.setTile(0, 0, spareTile);
		
		board.draw();
	}

}

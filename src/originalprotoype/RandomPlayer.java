import java.util.Random;

public class RandomPlayer implements Solver {

	private Board.Player myColor;

	public RandomPlayer(Board.Player color) {
		myColor= color;
	}
	
    public @Override Move[] getMoves(Board b) {
    	Random rand= new Random();
		int column= rand.nextInt(Board.NUM_COLS);
		while (b.getTile(0, column) != null) {
			column= rand.nextInt(Board.NUM_COLS);
		}
		Move[] move= { new Move(myColor, column) };
		return move;
	}
	
}

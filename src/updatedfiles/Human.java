/**********************************************************
 *   Project: Connect 4 Learning
 *
 *   Team 4:
 *		Sean Clark
 *		Thomas Coppola
 *		Anthony Fontanetta
 *		Sanjana Thomas
 **********************************************************/
import java.util.concurrent.Semaphore;

public class Human implements Solver {

	private Board.Player player; // The player
	private Semaphore waitSema;  // Used to wait for a move by the human
	private int nextColumn;      // Place to put next piece

	/** Construct a Human Solver for player p. */
	public Human(Board.Player p) {
		player= p;
		waitSema= new Semaphore(0);
	}


    public @Override Move[] getMoves(Board b) {
    	try {
			waitSema.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Move[]{ new Move(player, nextColumn) };
	}
	
	/** Signal to this Human that the user wants to place
	 *  a piece in column c. */
	public void columnClicked(int c) {
		nextColumn= c;
		waitSema.release();
	}

}
